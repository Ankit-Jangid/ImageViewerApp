package com.samples.imageviewer.ui.detail

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samples.imageviewer.data.local.FavoriteImage
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ImageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val imageId: String = savedStateHandle.get("imageId") ?: ""

    private val _isFavorited = MutableStateFlow(false)
    val isFavorited: StateFlow<Boolean> = _isFavorited

    // process cache hit and cache miss
    private val _image = MutableStateFlow(
        UnsplashImage(
            "", "",
            0, 0, null, null, 0
        )
    )
    val image: StateFlow<UnsplashImage> = _image

    fun fetchImage(imageId: String) {
        viewModelScope.launch {
            repository.getPhotoFromId(imageId)?.let {
                _image.value = it
            }
            repository.isFavorited(imageId).collect { isFav ->
                _isFavorited.value = isFav
            }
        }
    }


    fun toggleFavorite() {
        viewModelScope.launch {
            image.let { image ->
                val favoriteEntity = FavoriteImage(
                    id = image.value.id,
                    imageUrl = image.value.urls?.regular ?: "",
                    photographer = image.value.user?.name ?: ""
                )
                if (_isFavorited.value) {
                    repository.removeFromFavorites(favoriteEntity)
                } else {
                    repository.addToFavorites(favoriteEntity)
                }
            }
        }
    }


    fun downloadImage(context: Context, imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("DetailViewModel", "trying downloading image.")
                val client = OkHttpClient()
                val request = Request.Builder().url(imageUrl).build()
                val response = client.newCall(request).execute()
                val inputStream = response.body?.byteStream()

                if (inputStream != null) {
                    val filename = "unsplash_${System.currentTimeMillis()}.jpg"
                    val resolver = context.contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    val uri =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    uri?.let {
                        resolver.openOutputStream(it)?.use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    Log.d("DetailViewModel", "downloading image completed.")
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error downloading image: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}