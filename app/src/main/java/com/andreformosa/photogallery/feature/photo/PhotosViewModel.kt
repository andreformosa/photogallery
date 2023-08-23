package com.andreformosa.photogallery.feature.photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreformosa.photogallery.common.Result
import com.andreformosa.photogallery.common.asResult
import com.andreformosa.photogallery.data.albums.AlbumsRepository
import com.andreformosa.photogallery.data.model.local.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    albumsRepository: AlbumsRepository
) : ViewModel() {

    private val albumId: Int = checkNotNull(savedStateHandle["albumId"])

    private val photos: Flow<Result<List<Photo>>> =
        albumsRepository.getPhotosForAlbum(albumId).asResult()

    val uiState: StateFlow<PhotosUiState> = photos.map { photosResult ->
        when (photosResult) {
            is Result.Success -> PhotosUiState.Success(photosResult.data)
            is Result.Loading -> PhotosUiState.Loading
            is Result.Error -> PhotosUiState.EmptyOrErrorState
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PhotosUiState.Loading
        )
}

sealed interface PhotosUiState {
    object Loading : PhotosUiState
    data class Success(val photos: List<Photo>) : PhotosUiState
    object EmptyOrErrorState : PhotosUiState
}
