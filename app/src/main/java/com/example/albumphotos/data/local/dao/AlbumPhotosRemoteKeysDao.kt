package com.example.albumphotos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.albumphotos.model.AlbumPhotosRemoteKeys

@Dao
interface AlbumPhotosRemoteKeysDao {

    @Query("SELECT * FROM albumphotos_remote_keys_table WHERE id = :id")
    fun getRemoteKeys(id: String): AlbumPhotosRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<AlbumPhotosRemoteKeys>)

    @Query("DELETE FROM albumphotos_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}