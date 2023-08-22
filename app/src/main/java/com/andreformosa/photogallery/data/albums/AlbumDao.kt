package com.andreformosa.photogallery.data.albums

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.photogallery.data.model.local.Album

@Dao
interface AlbumDao {

//    @Query("SELECT * FROM album")
//    suspend fun getAll(): List<Album>

    @Query("SELECT * FROM album ORDER BY page")
    fun getAll(): PagingSource<Int, Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM album")
    suspend fun clearAllAlbums()
}
