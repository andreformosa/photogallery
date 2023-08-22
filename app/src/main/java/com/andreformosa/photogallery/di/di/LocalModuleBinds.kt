package com.andreformosa.photogallery.di.di

import com.andreformosa.photogallery.data.albums.LocalAlbumsDataSource
import com.andreformosa.photogallery.data.albums.RoomAlbumsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalModuleBinds {

    @Binds
    abstract fun bindLocalAlbumsDataSource(bind: RoomAlbumsDataSource): LocalAlbumsDataSource
}
