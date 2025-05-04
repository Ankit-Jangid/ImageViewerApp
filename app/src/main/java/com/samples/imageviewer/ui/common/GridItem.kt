package com.samples.imageviewer.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.samples.imageviewer.data.model.UnsplashImage

/**
 * Size comparison for a example image:
 * raw/full : 3.4MB
 * regular : 400KB
 * small : 61KB
 * thumb: 19KB
 *
 * regular and small can be used for list
 * */

@Composable
fun GridImageItem(image: UnsplashImage, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        AsyncImage(
            model = image.urls?.small,
            contentDescription = image.description,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = image.user?.name?.trim() ?: "", modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

