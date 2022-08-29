package com.example.albumphotos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.albumphotos.util.Constants.ALBUMPHOTOS_REMOTE_KEYS_TABLE

@Entity(tableName = ALBUMPHOTOS_REMOTE_KEYS_TABLE)
data class AlbumPhotosRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
