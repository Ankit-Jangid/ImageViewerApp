package com.samples.imageviewer.repository

import com.samples.imageviewer.data.api.UnsplashApiService
import com.samples.imageviewer.data.local.FavoriteImage
import com.samples.imageviewer.data.model.UnsplashImage
import com.samples.imageviewer.data.api.Result
import com.samples.imageviewer.data.local.ImageDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val api: UnsplashApiService,
    private val dao: ImageDao
) {
    // in-mem cache to avoid api calls
    private val photoCache = mutableMapOf<String, UnsplashImage>()

    suspend fun fetchImages(): Result<List<UnsplashImage>> {
        return try {
            val photos = api.getPhotos() ?: return Result.Error("Unknown error", null)
            photos.forEach {
                photoCache[it.id] = it
            }
            Result.Success(photos)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    //we're saving the API call here by using in memory cache.
    // we need url only for now,so we're storing image objects in cache.
    suspend fun getPhotoFromId(id: String): UnsplashImage? {
        return try {
            //if cache hit then return
            photoCache[id]?.let { return it }

            //on cache miss make api call
            val photo = api.getPhotoById(id)
            photo?.let { photoCache[id] = it }
            photo
        } catch (e: Exception) {
            null
        }

    }

    //load multiple images by ids
    suspend fun getImagesByIds(ids: List<String>): List<UnsplashImage> {
        return ids.mapNotNull { id ->
            try {
                api.getPhotoById(id)
            } catch (e: Exception) {
                null
            }
        }
    }


    fun getFavorites() = dao.getFavorites()

    suspend fun addToFavorites(image: FavoriteImage) = dao.addFavorite(image)

    suspend fun removeFromFavorites(image: FavoriteImage) = dao.removeFavorite(image)

    fun isFavorited(imageId: String): Flow<Boolean> {
        return dao.isFavorited(imageId)
    }

}