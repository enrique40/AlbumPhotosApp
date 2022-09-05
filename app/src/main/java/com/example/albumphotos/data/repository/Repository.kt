package com.example.albumphotos.data.repository

import AlbumPhotosRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.albumphotos.data.local.AlbumPhotosDatabase
import com.example.albumphotos.data.remote.AlbumPhotosApi
import com.example.albumphotos.model.AlbumPhotosImage
import com.example.albumphotos.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val albumPhotosApi: AlbumPhotosApi,
    private val albumPhotosDatabase: AlbumPhotosDatabase
) {

    fun getAllImages(): Flow<PagingData<AlbumPhotosImage>> {
        val pagingSourceFactory = { albumPhotosDatabase.albumPhotosImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = AlbumPhotosRemoteMediator(
                albumPhotosApi = albumPhotosApi,
                albumPhotosDatabase = albumPhotosDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}