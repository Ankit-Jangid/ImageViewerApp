package com.samples.imageviewer.ui.favourite

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.ui.common.GridImageItem

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel, onImageClick: (UnsplashImage) -> Unit) {
    val favorites by viewModel.favorites.collectAsState()
    viewModel.loadImagesByIds(favorites.map { it.id })
    val images by viewModel.images.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(images.size) { index ->
            GridImageItem(
                image = images.get(index),
                onClick = { onImageClick(images.get(index)) })
        }
    }
}


