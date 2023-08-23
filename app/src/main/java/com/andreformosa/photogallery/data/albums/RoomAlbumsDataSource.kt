package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomAlbumsDataSource @Inject constructor(
//    private val albumDao: AlbumDao,
    private val photoDao: PhotoDao
) : LocalAlbumsDataSource {

//    override suspend fun getAllAlbums(): List<Album> {
//        return albumDao.getAll()
//    }
//
//    override suspend fun insertAlbums(albums: List<Album>) {
//        albumDao.insertAll(albums)
//    }

    override fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>> {
        return photoDao.getPhotosForAlbum(albumId)
    }

    override suspend fun insertPhotos(photos: List<Photo>) {
        photoDao.insertAll(photos)
    }
}
