package com.andreformosa.photogallery.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreformosa.photogallery.data.albums.AlbumsRemoteMediator
import com.andreformosa.photogallery.data.api.JSONPlaceholderService
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import com.andreformosa.photogallery.data.model.local.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedAlbumsUseCase @Inject constructor(
    private val database: AlbumsDatabase,
    private val service: JSONPlaceholderService,
) {

    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(): Flow<PagingData<Album>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE + 10,
            ),
            pagingSourceFactory = {
                database.albumDao().getAll()
            },
            remoteMediator = AlbumsRemoteMediator(
                database,
                service
            )
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
