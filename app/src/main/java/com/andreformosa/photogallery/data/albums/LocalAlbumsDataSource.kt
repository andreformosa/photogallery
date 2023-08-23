package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.flow.Flow

interface LocalAlbumsDataSource {

//    suspend fun getAllAlbums(): List<Album>
//
//    suspend fun insertAlbums(albums: List<Album>)

    fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>>

    suspend fun insertPhotos(photos: List<Photo>)
}
