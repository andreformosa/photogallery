package com.andreformosa.photogallery.di.di

import com.andreformosa.photogallery.data.albums.AlbumsRepository
import com.andreformosa.photogallery.data.albums.OfflineFirstAlbumsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindAlbumsRepository(bind: OfflineFirstAlbumsRepository): AlbumsRepository
}
