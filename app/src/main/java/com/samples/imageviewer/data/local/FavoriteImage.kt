package com.samples.imageviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_images")
data class FavoriteImage(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val photographer: String
)