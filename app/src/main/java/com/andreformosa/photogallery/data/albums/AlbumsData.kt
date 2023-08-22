package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Album
import com.andreformosa.photogallery.data.model.local.Photo

sealed interface AlbumsResult {
    data class Success(val data: List<Album>) : AlbumsResult
    object GenericError : AlbumsResult
}

sealed interface AlbumPhotosResult {
    data class Success(val data: List<Photo>) : AlbumPhotosResult
    object GenericError : AlbumPhotosResult
}
