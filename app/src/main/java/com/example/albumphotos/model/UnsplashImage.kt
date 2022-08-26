package com.example.albumphotos.model

import androidx.room.Entity
import com.example.albumphotos.util.Constants.UNSPLASH_IMAGE_TABLE

@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    val id: String,
    val urls: Urls,
    val likes: Int,
    val user: User
)