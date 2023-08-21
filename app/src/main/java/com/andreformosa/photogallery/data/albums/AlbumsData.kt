package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo

sealed interface AlbumsResult {
    data class Success(val data: List<Album>) : AlbumsResult
    data object GenericError : AlbumsResult
}

sealed interface AlbumPhotosResult {
    data class Success(val data: List<Photo>) : AlbumPhotosResult
    data object GenericError : AlbumPhotosResult
}
