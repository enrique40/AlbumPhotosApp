package com.example.albumphotos.di

import android.content.Context
import androidx.room.Room
import com.example.albumphotos.data.local.AlbumPhotosDatabase
import com.example.albumphotos.util.Constants.ALBUMPHOTOS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providerDatabase(
        @ApplicationContext context: Context
    ): AlbumPhotosDatabase {
        return Room.databaseBuilder(
            context,
            AlbumPhotosDatabase::class.java,
            ALBUMPHOTOS_DATABASE
        ).build()
    }

}