package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.api.JSONPlaceholderService
import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class RetrofitAlbumsDataSource @Inject constructor(
    private val service: JSONPlaceholderService,
) : RemoteAlbumsDataSource {

    override suspend fun getAlbums(): ApiResponse<List<Album>> {
        return service.getAlbums()
    }

    override suspend fun getPhotosForAlbum(albumId: Int): ApiResponse<List<Photo>> {
        return service.getPhotosForAlbum(albumId)
    }
}
