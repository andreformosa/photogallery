package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>>

    suspend fun getPhoto(photoId: Int): Photo?
}
