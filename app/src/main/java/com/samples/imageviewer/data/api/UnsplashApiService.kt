package com.samples.imageviewer.data.api

import com.samples.imageviewer.data.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Path

interface UnsplashApiService {

    @GET("photos")
    suspend fun getPhotos(): List<UnsplashImage>?

    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: String): UnsplashImage?
}