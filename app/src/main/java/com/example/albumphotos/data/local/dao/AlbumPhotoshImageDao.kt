package com.example.albumphotos.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.albumphotos.model.AlbumPhotosImage

@Dao
interface AlbumPhotoshImageDao {

    @Query("SELECT * FROM albumphotos_image_table")
    fun getAllImages(): PagingSource<Int, AlbumPhotosImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<AlbumPhotosImage>)

    @Query("DELETE FROM albumphotos_image_table")
    suspend fun deleteAllImages()
}