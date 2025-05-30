package com.samples.imageviewer.di

import android.content.Context
import androidx.room.Room
import com.samples.imageviewer.data.local.AppDatabase
import com.samples.imageviewer.data.local.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "unsplash_database"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideImageDao(db: AppDatabase): ImageDao = db.favoriteDao()
}