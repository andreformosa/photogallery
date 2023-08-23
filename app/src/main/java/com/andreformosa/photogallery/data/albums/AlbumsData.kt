package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Album

// TODO: Consider deleting
sealed interface AlbumsResult {
    data class Success(val data: List<Album>) : AlbumsResult
    object GenericError : AlbumsResult
}
