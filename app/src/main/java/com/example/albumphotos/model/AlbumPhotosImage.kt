package com.example.albumphotos.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.albumphotos.util.Constants.ALBUMPHOTOS_IMAGE_TABLE

@Entity(tableName = ALBUMPHOTOS_IMAGE_TABLE)
data class AlbumPhotosImage(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User
)