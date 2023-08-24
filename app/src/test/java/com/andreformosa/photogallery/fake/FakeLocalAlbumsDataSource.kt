package com.andreformosa.photogallery.fake

import com.andreformosa.photogallery.data.albums.LocalAlbumsDataSource
import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeLocalAlbumsDataSource : LocalAlbumsDataSource {

    private val photosFlow =
        MutableSharedFlow<List<Photo>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>> {
        return photosFlow
    }

    override suspend fun getPhoto(photoId: Int): Photo? {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun insertPhotos(photos: List<Photo>) {
        photosFlow.emit(photos)
    }
}
