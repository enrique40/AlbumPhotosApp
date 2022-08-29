package com.example.albumphotos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.albumphotos.data.local.dao.AlbumPhotosRemoteKeysDao
import com.example.albumphotos.data.local.dao.AlbumPhotoshImageDao
import com.example.albumphotos.model.AlbumPhotosImage
import com.example.albumphotos.model.AlbumPhotosRemoteKeys

@Database(entities = [AlbumPhotosImage::class, AlbumPhotosRemoteKeys::class], version = 1)
abstract class AlbumPhotosDatabase: RoomDatabase() {

    abstract fun albumPhotosImageDao(): AlbumPhotoshImageDao
    abstract fun albumPhotosRemoteKeysDao(): AlbumPhotosRemoteKeysDao
}