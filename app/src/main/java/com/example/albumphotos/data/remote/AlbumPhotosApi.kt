package com.example.albumphotos.data.remote

import com.example.albumphotos.BuildConfig
import com.example.albumphotos.model.AlbumPhotosImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface AlbumPhotosApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<AlbumPhotosImage>

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<AlbumPhotosImage>
}