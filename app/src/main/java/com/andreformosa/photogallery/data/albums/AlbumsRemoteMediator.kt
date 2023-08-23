package com.andreformosa.photogallery.data.albums

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import com.andreformosa.photogallery.data.model.local.Album
import com.andreformosa.photogallery.data.model.local.AlbumWithPhotos
import com.andreformosa.photogallery.data.model.local.RemoteKey
import com.andreformosa.photogallery.data.model.remote.asAlbumEntity
import com.andreformosa.photogallery.data.model.remote.asPhotoEntity
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class AlbumsRemoteMediator(
    private val database: AlbumsDatabase,
    private val remoteAlbumsDataSource: RemoteAlbumsDataSource,
) : RemoteMediator<Int, AlbumWithPhotos>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumWithPhotos>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val albums = when (val albumsResponse = remoteAlbumsDataSource.getAlbums(page = page)) {
                is ApiResponse.Success -> albumsResponse.data.map { it.asAlbumEntity(page) }
                is ApiResponse.Failure.Error -> throw HttpException(albumsResponse.response)
                is ApiResponse.Failure.Exception -> throw IOException("Request failed")
            }

            val albumsWithPhotos = fetchPhotosForAlbums(albums)

            val endOfPaginationReached = albums.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().clearRemoteKeys()
                    database.albumDao().clearAllAlbums()
                    database.photoDao().clearAllPhotos()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = albums.map {
                    RemoteKey(
                        albumId = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                database.remoteKeyDao().insertAll(remoteKeys)
                database.albumDao()
                    .insertAll(albums.onEachIndexed { _, album -> album.page = page })
                database.photoDao()
                    .insertAll(albumsWithPhotos.flatMap { albumWithPhotos -> albumWithPhotos.photos })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val dbCreationTime = database.remoteKeyDao().getCreationTime() ?: 0

        // If database does not exist, fetch data from network, otherwise load from cache
        return if (dbCreationTime == 0L) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AlbumWithPhotos>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.album?.id?.let { id ->
                database.remoteKeyDao().getRemoteKeyByAlbumId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AlbumWithPhotos>): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { albumWithPhotos ->
            database.remoteKeyDao().getRemoteKeyByAlbumId(albumWithPhotos.album.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AlbumWithPhotos>): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { albumWithPhotos ->
            database.remoteKeyDao().getRemoteKeyByAlbumId(albumWithPhotos.album.id)
        }
    }

    /**
     * For each album in the list, run an async request to get its photos.
     * Once all of the async requests finish, return a list with [AlbumWithPhotos].
     */
    private suspend fun fetchPhotosForAlbums(albums: List<Album>): List<AlbumWithPhotos> =
        coroutineScope {
            val albumsWithPhotos = mutableListOf<AlbumWithPhotos>()

            val deferredAlbums = albums.map { album ->
                async {
                    val photosResponse = remoteAlbumsDataSource.getPhotosForAlbum(album.id)
                    val photos = if (photosResponse is ApiResponse.Success) {
                        photosResponse.data.map { it.asPhotoEntity() }
                    } else {
                        // In an ideal scenario, this case would be handled differently (such as a retry)
                        emptyList()
                    }
                    AlbumWithPhotos(album, photos)
                }
            }

            albumsWithPhotos.addAll(deferredAlbums.awaitAll())

            albumsWithPhotos
        }
}
