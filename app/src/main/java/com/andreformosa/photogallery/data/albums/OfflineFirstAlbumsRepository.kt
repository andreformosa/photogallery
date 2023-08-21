package com.andreformosa.photogallery.data.albums

import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstAlbumsRepository @Inject constructor(
    private val remoteAlbumsDataSource: RemoteAlbumsDataSource,
) : AlbumsRepository {

    // TODO: Implement caching
    override suspend fun getAlbums(): AlbumsResult {
        return when (val response = remoteAlbumsDataSource.getAlbums()) {
            is ApiResponse.Success -> {
                AlbumsResult.Success(response.data)
            }

            is ApiResponse.Failure.Error,
            is ApiResponse.Failure.Exception -> AlbumsResult.GenericError
        }
    }

    override suspend fun getPhotosForAlbum(albumId: Int): AlbumPhotosResult {
        return when (val response = remoteAlbumsDataSource.getPhotosForAlbum(albumId)) {
            is ApiResponse.Success -> {
                AlbumPhotosResult.Success(response.data)
            }

            is ApiResponse.Failure.Error,
            is ApiResponse.Failure.Exception -> AlbumPhotosResult.GenericError
        }
    }
}
