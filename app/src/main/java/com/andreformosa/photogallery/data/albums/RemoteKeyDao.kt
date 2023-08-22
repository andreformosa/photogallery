package com.andreformosa.photogallery.data.albums

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.photogallery.data.model.local.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_key WHERE albumId = :id")
    suspend fun getRemoteKeyByAlbumId(id: Int): RemoteKey?

    @Query("DELETE FROM remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT createdAt FROM remote_key ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
