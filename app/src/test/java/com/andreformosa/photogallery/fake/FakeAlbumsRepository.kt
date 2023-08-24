package com.andreformosa.photogallery.fake

import com.andreformosa.photogallery.data.albums.AlbumsRepository
import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class FakeAlbumsRepository : AlbumsRepository {

    private val photosFlow =
        MutableSharedFlow<List<Photo>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>> {
        return photosFlow
    }

    override suspend fun getPhoto(photoId: Int): Photo? {
        return photosFlow.first().first()
    }

    // A test-only API to allow controlling the list of photos from tests
    suspend fun sendPhotos(photos: List<Photo>) {
        photosFlow.emit(photos)
    }
}
