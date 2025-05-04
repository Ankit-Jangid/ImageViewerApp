package com.samples.imageviewer.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM favorite_images")
    fun getFavorites(): Flow<List<FavoriteImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(image: FavoriteImage)

    @Delete
    suspend fun removeFavorite(image: FavoriteImage)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_images WHERE id = :imageId)")
    fun isFavorited(imageId: String): Flow<Boolean>
}