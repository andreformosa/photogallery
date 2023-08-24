package com.andreformosa.photogallery.di

import android.content.Context
import androidx.room.Room.inMemoryDatabaseBuilder
import com.andreformosa.photogallery.data.albums.AlbumDao
import com.andreformosa.photogallery.data.albums.PhotoDao
import com.andreformosa.photogallery.data.albums.RemoteKeyDao
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
object TestDatabaseModule {

    @Singleton
    @Provides
    fun provideInMemoryAlbumsDatabase(
        @ApplicationContext context: Context
    ): AlbumsDatabase {
        return inMemoryDatabaseBuilder(context, AlbumsDatabase::class.java).build()
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

    @Singleton
    @Provides
    fun provideRemoteKeysDao(albumsDatabase: AlbumsDatabase): RemoteKeyDao {
        return albumsDatabase.remoteKeyDao()
    }
}
