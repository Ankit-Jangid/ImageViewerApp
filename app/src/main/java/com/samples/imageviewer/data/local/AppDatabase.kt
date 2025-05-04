package com.samples.imageviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteImage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): ImageDao
}