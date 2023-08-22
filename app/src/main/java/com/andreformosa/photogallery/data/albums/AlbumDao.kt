package com.andreformosa.photogallery.data.albums

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andreformosa.photogallery.data.model.local.Album
import com.andreformosa.photogallery.data.model.local.AlbumWithPhotos

@Dao
interface AlbumDao {

    @Transaction
    @Query("SELECT * FROM album ORDER BY page")
    fun getAlbumsWithPhotos(): PagingSource<Int, AlbumWithPhotos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM album")
    suspend fun clearAllAlbums()
}
