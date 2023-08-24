package com.andreformosa.photogallery.di

import android.content.Context
import com.andreformosa.photogallery.data.store.CacheInfoDataStore
import com.andreformosa.photogallery.data.store.CacheInfoStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideCacheInfoStore(
        @ApplicationContext context: Context,
    ): CacheInfoStore {
        return CacheInfoDataStore(context)
    }
}
