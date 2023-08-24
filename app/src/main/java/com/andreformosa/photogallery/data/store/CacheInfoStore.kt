package com.andreformosa.photogallery.data.store

interface CacheInfoStore {

    suspend fun setCacheUpdateTime(time: Long)

    suspend fun getCacheUpdateTime(): Long
}
