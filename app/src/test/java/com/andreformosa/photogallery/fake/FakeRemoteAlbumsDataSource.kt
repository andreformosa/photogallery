package com.andreformosa.photogallery.fake

import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class FakeRemoteAlbumsDataSource : RemoteAlbumsDataSource {

    private val responseFlow =
        MutableSharedFlow<ApiResponse<List<Photo>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    override suspend fun getAlbums(page: Int?): ApiResponse<List<Album>> {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun getPhotosForAlbum(albumId: Int): ApiResponse<List<Photo>> {
        return responseFlow.first()
    }

    // A test-only API to allow controlling the response from tests
    suspend fun sendResponse(response: ApiResponse<List<Photo>>) {
        responseFlow.emit(response)
    }
}
