package com.andreformosa.photogallery.fake

import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class FakeRemoteAlbumsDataSource @Inject constructor() : RemoteAlbumsDataSource {

    private val albumsResponseFlow =
        MutableSharedFlow<ApiResponse<List<Album>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    // Using StateFlow due to concurrency issue in Instrumentation tests
    private val photosResponseFlow =
        MutableStateFlow<ApiResponse<List<Photo>>>(ApiResponse.Success(Response.success(emptyList())))

    override suspend fun getAlbums(page: Int?): ApiResponse<List<Album>> {
        return albumsResponseFlow.first()
    }

    override suspend fun getPhotosForAlbum(albumId: Int): ApiResponse<List<Photo>> {
        return this.photosResponseFlow.first()
    }

    suspend fun sendAlbumsResponse(response: ApiResponse<List<Album>>) {
        albumsResponseFlow.emit(response)
    }

    suspend fun sendPhotosResponse(response: ApiResponse<List<Photo>>) {
        this.photosResponseFlow.emit(response)
    }
}
