package com.andreformosa.photogallery.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreformosa.photogallery.data.albums.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository
) : ViewModel() {

    private val photoId: Int = checkNotNull(savedStateHandle["photoId"])

    private val _uiState = MutableStateFlow<PhotoDetailsUiState>(PhotoDetailsUiState.Loading)
    val uiState: StateFlow<PhotoDetailsUiState> = _uiState

    init {
        viewModelScope.launch {
            val photo = albumsRepository.getPhoto(photoId)
            if (photo != null) {
                _uiState.value = PhotoDetailsUiState.Success(photo.title, photo.url)
            } else {
                // This is an exceptional case and should never happen.
                // In a production app, it would make sense to handle this more gracefully.
                Timber.e("Photo not found")
            }
        }
    }
}

sealed interface PhotoDetailsUiState {
    object Loading : PhotoDetailsUiState
    data class Success(val title: String, val url: String) : PhotoDetailsUiState
}
