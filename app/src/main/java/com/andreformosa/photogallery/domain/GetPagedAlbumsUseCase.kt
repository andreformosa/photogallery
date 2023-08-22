package com.andreformosa.photogallery.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreformosa.photogallery.data.albums.AlbumsRemoteMediator
import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import com.andreformosa.photogallery.data.model.local.AlbumWithPhotos
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedAlbumsUseCase @Inject constructor(
    private val database: AlbumsDatabase,
    private val remoteDataSource: RemoteAlbumsDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(): Flow<PagingData<AlbumWithPhotos>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE + 10,
            ),
            pagingSourceFactory = {
                database.albumDao().getAlbumsWithPhotos()
            },
            remoteMediator = AlbumsRemoteMediator(
                database,
                remoteDataSource
            )
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
