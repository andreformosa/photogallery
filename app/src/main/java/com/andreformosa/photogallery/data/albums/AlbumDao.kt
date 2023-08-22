package com.andreformosa.photogallery.data.albums

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.photogallery.data.model.local.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    suspend fun getAll(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)
}
