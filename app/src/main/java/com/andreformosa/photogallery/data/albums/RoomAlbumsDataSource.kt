package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomAlbumsDataSource @Inject constructor(
    private val photoDao: PhotoDao
) : LocalAlbumsDataSource {

    override fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>> {
        return photoDao.getPhotosForAlbum(albumId)
    }

    override suspend fun getPhoto(photoId: Int): Photo? {
        return photoDao.getPhotoById(photoId)
    }

    override suspend fun insertPhotos(photos: List<Photo>) {
        photoDao.insertAll(photos)
    }
}
