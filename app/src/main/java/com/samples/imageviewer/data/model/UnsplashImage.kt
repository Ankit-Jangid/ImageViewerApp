package com.samples.imageviewer.data.model

data class UnsplashImage(
    val id: String = "",
    val description: String?,
    val width: Long,
    val height: Long,
    val urls: Urls?,
    val user: User?,
    val likes: Int,
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
)

data class User(
    val id: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val profile_image: ProfileImage
)

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String,
)