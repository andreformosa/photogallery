package com.andreformosa.photogallery.data.albums

import com.andreformosa.photogallery.data.model.local.Photo
import com.andreformosa.photogallery.data.model.remote.asPhotoEntity
import com.andreformosa.photogallery.data.store.CacheInfoStore
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstAlbumsRepository @Inject constructor(
    private val remoteDataSource: RemoteAlbumsDataSource,
    private val localDataSource: LocalAlbumsDataSource,
    private val cacheInfoStore: CacheInfoStore
) : AlbumsRepository {

//    /**
//     * Emit cached albums if they exist.
//     * If no cache exists or cache is stale, fetch from network, cache to database and emit.
//     */
//    override suspend fun getAlbums(): Flow<AlbumsResult> = flow {
//        val cachedAlbums = localDataSource.getAllAlbums()
//        if (cachedAlbums.isNotEmpty()) {
//            emit(AlbumsResult.Success(cachedAlbums))
//        }
//
//        if (isCacheStale() || cachedAlbums.isEmpty()) {
//            when (val response = remoteDataSource.getAlbums()) {
//                is ApiResponse.Success -> {
//                    // Save albums to local storage
//                    localDataSource.insertAlbums(response.data.map { it.asAlbumEntity() })
//
//                    // Update cache time
//                    cacheInfoStore.setCacheUpdateTime(System.currentTimeMillis())
//
//                    // Get albums from local storage which is the source of truth
//                    emit(AlbumsResult.Success(localDataSource.getAllAlbums()))
//                }
//
//                is ApiResponse.Failure.Error,
//                is ApiResponse.Failure.Exception -> emit(AlbumsResult.GenericError)
//            }
//        }
//    }.flowOn(Dispatchers.IO)

    /**
     * Emit cached photos if they exist.
     * If no cache exists or cache is stale, fetch from network, cache to database and emit.
     */
    override fun getPhotosForAlbum(albumId: Int): Flow<List<Photo>> {
        return localDataSource.getPhotosForAlbum(albumId)
            .onEach { photos ->
                if (photos.isEmpty() || isCacheStale()) {
                    remoteDataSource.getPhotosForAlbum(albumId).also { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                // Update cache time
                                cacheInfoStore.setCacheUpdateTime(System.currentTimeMillis())

                                // Save photos to local storage
                                localDataSource.insertPhotos((response.data.map { it.asPhotoEntity() }))
                            }

                            is ApiResponse.Failure.Error,
                            is ApiResponse.Failure.Exception -> {
                                Timber.e("Error fetching photos for album: $albumId")
                            }
                        }
                    }
                }
            }
    }

    private suspend fun isCacheStale(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastFetchTime = cacheInfoStore.getCacheUpdateTime()

        // Check if data needs to be refreshed based on the time interval (5 minutes in milliseconds)
        return currentTime - lastFetchTime > TimeUnit.MINUTES.toMillis(5)
    }
}
