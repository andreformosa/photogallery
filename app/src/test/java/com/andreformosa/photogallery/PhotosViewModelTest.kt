package com.andreformosa.photogallery

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.andreformosa.photogallery.factory.createEntityPhoto
import com.andreformosa.photogallery.fake.FakeAlbumsRepository
import com.andreformosa.photogallery.feature.photo.PhotosUiState
import com.andreformosa.photogallery.feature.photo.PhotosViewModel
import com.andreformosa.photogallery.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val savedStateHandle = SavedStateHandle(mapOf("albumId" to 0))
    private val albumsRepository = FakeAlbumsRepository()

    private lateinit var viewModel: PhotosViewModel

    @Before
    fun setup() {
        viewModel = PhotosViewModel(
            savedStateHandle = savedStateHandle,
            albumsRepository = albumsRepository
        )
    }

    @Test
    fun `initial state is loading`() = runTest {
        val expectedState: PhotosUiState = PhotosUiState.Loading

        assertThat(viewModel.uiState.value).isEqualTo(expectedState)
    }

    @Test
    fun `state changes from loading to success when data is available`() = runTest {
        val photos = List(3) { createEntityPhoto(id = it + 1, title = "Photo ${it + 1}") }

        viewModel.uiState.test {
            val firstItem = awaitItem()
            assertThat(firstItem).isEqualTo(PhotosUiState.Loading)

            albumsRepository.sendPhotos(photos)

            val secondItem = awaitItem()
            assertThat(secondItem).isEqualTo(PhotosUiState.Success(photos))
        }
    }
}
