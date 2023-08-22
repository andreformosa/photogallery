package com.andreformosa.photogallery.data.albums

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.photogallery.data.model.local.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo WHERE albumId = :albumId")
    suspend fun getPhotosForAlbum(albumId: Int): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("DELETE FROM photo")
    suspend fun clearAllPhotos()
}
