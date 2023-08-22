package com.andreformosa.photogallery.data.albums

import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    suspend fun getAlbums(): Flow<AlbumsResult>

    suspend fun getPhotosForAlbum(albumId: Int): Flow<AlbumPhotosResult>
}
