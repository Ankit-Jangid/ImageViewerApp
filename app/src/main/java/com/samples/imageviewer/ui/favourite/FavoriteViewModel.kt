package com.samples.imageviewer.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samples.imageviewer.data.local.FavoriteImage
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {


    val favorites = repository.getFavorites().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images: StateFlow<List<UnsplashImage>> = _images

    fun loadImagesByIds(ids: List<String>) {
        viewModelScope.launch {
            val result = repository.getImagesByIds(ids)
            _images.value = result
        }
    }

}