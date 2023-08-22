package com.andreformosa.photogallery.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheInfoStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_CACHE_UPDATE_TIME = longPreferencesKey("albums_update_time")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

    suspend fun setCacheUpdateTime(time: Long) {
        context.dataStore.edit { session ->
            session[KEY_CACHE_UPDATE_TIME] = time
        }
    }

    suspend fun getCacheUpdateTime(): Long {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_CACHE_UPDATE_TIME] ?: 0L
    }
}
