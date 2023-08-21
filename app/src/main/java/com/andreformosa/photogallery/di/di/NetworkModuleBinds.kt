package com.andreformosa.photogallery.di.di

import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import com.andreformosa.photogallery.data.albums.RetrofitAlbumsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModuleBinds {

    @Binds
    abstract fun bindRemoteAlbumsDataSource(bind: RetrofitAlbumsDataSource): RemoteAlbumsDataSource
}
