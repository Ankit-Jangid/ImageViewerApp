package com.samples.imageviewer.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samples.imageviewer.data.api.Result
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.ui.common.GridImageItem
import com.samples.imageviewer.ui.common.ShimmerImageItem


@Composable
fun HomeScreen(viewModel: HomeViewModel, onImageClick: (UnsplashImage) -> Unit) {
    val imagesState by viewModel.images.collectAsState()

    when (imagesState) {
        is Result.Loading -> {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
                items(10) {
                    ShimmerImageItem()
                }
            }
        }
        is Result.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = imagesState.message ?: "Unknown error")
            }
        }
        is Result.Success -> {
            val images = (imagesState as Result.Success<List<UnsplashImage>>).data
            if (images.isNullOrEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No images found")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(images.size) { index ->
                        GridImageItem(
                            image = images.get(index),
                            onClick = { onImageClick(images.get(index)) })
                    }
                }
            }
        }
    }
}
