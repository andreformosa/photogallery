package com.andreformosa.photogallery.di.di

import android.content.Context
import androidx.room.Room
import com.andreformosa.photogallery.data.albums.AlbumDao
import com.andreformosa.photogallery.data.albums.PhotoDao
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAlbumsDatabase(
        @ApplicationContext context: Context
    ): AlbumsDatabase {
        return Room
            .databaseBuilder(context, AlbumsDatabase::class.java, "albums-database")
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(albumsDatabase: AlbumsDatabase): AlbumDao {
        return albumsDatabase.albumDao()
    }

    @Singleton
    @Provides
    fun providePhotoDao(albumsDatabase: AlbumsDatabase): PhotoDao {
        return albumsDatabase.photoDao()
    }
}
