package com.andreformosa.photogallery.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CacheInfoDataStore @Inject constructor(
    private val context: Context
) : CacheInfoStore {

    companion object {
        private val KEY_CACHE_UPDATE_TIME = longPreferencesKey("cache_update_time")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

    override suspend fun setCacheUpdateTime(time: Long) {
        context.dataStore.edit { session ->
            session[KEY_CACHE_UPDATE_TIME] = time
        }
    }

    override suspend fun getCacheUpdateTime(): Long {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_CACHE_UPDATE_TIME] ?: 0L
    }
}
