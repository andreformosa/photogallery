package com.andreformosa.photogallery.fake

import com.andreformosa.photogallery.data.store.CacheInfoStore

class FakeCacheInfoStore : CacheInfoStore {

    private var cacheUpdateTime: Long = 0

    override suspend fun setCacheUpdateTime(time: Long) {
        cacheUpdateTime = time
    }

    override suspend fun getCacheUpdateTime(): Long {
        return cacheUpdateTime
    }
}
