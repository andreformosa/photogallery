package com.andreformosa.photogallery.di

import com.andreformosa.photogallery.fake.FakeRemoteAlbumsDataSource
import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModuleBinds::class]
)
@Module
abstract class TestNetworkModuleBinds {

    @Binds
    abstract fun bindRemoteAlbumsDataSource(bind: FakeRemoteAlbumsDataSource): RemoteAlbumsDataSource
}
