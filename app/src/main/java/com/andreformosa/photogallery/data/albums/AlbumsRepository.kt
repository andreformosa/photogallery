package com.andreformosa.photogallery.data.albums

interface AlbumsRepository {

    suspend fun getAlbums(): AlbumsResult

    suspend fun getPhotosForAlbum(albumId: Int): AlbumPhotosResult
}
