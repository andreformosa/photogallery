package com.andreformosa.photogallery.data.albums

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo WHERE albumId = :albumId")
    fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>>

    @Query("SELECT * FROM photo WHERE id = :photoId")
    suspend fun getPhotoById(photoId: Int): Photo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("DELETE FROM photo")
    suspend fun clearAllPhotos()
}
