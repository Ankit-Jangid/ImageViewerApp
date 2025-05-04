package com.samples.imageviewer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.samples.imageviewer.data.api.Result


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _images = MutableStateFlow<Result<List<UnsplashImage>>>(Result.Loading())
    val images: StateFlow<Result<List<UnsplashImage>>> = _images.asStateFlow()

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            _images.value = repository.fetchImages()
        }
    }
}