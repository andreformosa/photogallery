package com.andreformosa.photogallery.feature.album

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.andreformosa.photogallery.data.model.local.AlbumWithPhotos
import com.andreformosa.photogallery.domain.GetPagedAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getPagedAlbumsUseCase: GetPagedAlbumsUseCase
) : ViewModel() {

    val albumsWithPhotos: Flow<PagingData<AlbumWithPhotos>> = getPagedAlbumsUseCase.invoke()
}
