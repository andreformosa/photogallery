package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo
import com.skydoves.sandwich.ApiResponse

interface RemoteAlbumsDataSource {

    suspend fun getAlbums(): ApiResponse<List<Album>>

    suspend fun getPhotosForAlbum(albumId: Int): ApiResponse<List<Photo>>
}
