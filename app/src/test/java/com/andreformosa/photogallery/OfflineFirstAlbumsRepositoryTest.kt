package com.andreformosa.photogallery

import app.cash.turbine.test
import com.andreformosa.photogallery.data.albums.OfflineFirstAlbumsRepository
import com.andreformosa.photogallery.factory.createEntityPhoto
import com.andreformosa.photogallery.factory.createResponsePhoto
import com.andreformosa.photogallery.fake.FakeCacheInfoStore
import com.andreformosa.photogallery.fake.FakeLocalAlbumsDataSource
import com.andreformosa.photogallery.fake.FakeRemoteAlbumsDataSource
import com.andreformosa.photogallery.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class OfflineFirstAlbumsRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remoteDataSource = FakeRemoteAlbumsDataSource()
    private val localDataSource = FakeLocalAlbumsDataSource()
    private val cacheInfoStore = FakeCacheInfoStore()

    private lateinit var repository: OfflineFirstAlbumsRepository

    @Before
    fun setUp() {
        repository = OfflineFirstAlbumsRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            cacheInfoStore = cacheInfoStore
        )
    }

    @Test
    fun `get photos for album - from remote when cache is empty`() = runTest {
        // Arrange
        val responsePhotos = List(3) { createResponsePhoto(id = it + 1, title = "Photo ${it + 1}") }

        cacheInfoStore.setCacheUpdateTime(0)
        localDataSource.insertPhotos(emptyList())
        remoteDataSource.sendResponse(ApiResponse.Success(Response.success(responsePhotos)))

        // Act
        val actualPhotos = repository.getPhotosForAlbum(1)

        // Assert
        actualPhotos.test {
            val firstItem = awaitItem()
            assertThat(firstItem.size).isEqualTo(0)

            val secondItem = awaitItem()
            assertThat(secondItem.size).isEqualTo(responsePhotos.size)

            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get photos for album - from cache when cache is not empty and not expired`() = runTest {
        // Arrange
        val photos = List(3) { createEntityPhoto(id = it + 1, title = "Photo ${it + 1}") }
        cacheInfoStore.setCacheUpdateTime(System.currentTimeMillis())
        localDataSource.insertPhotos(photos)

        // Act
        val actualPhotos = repository.getPhotosForAlbum(1)

        // Assert
        actualPhotos.test {
            val firstItem = awaitItem()
            assertThat(firstItem.size).isEqualTo(photos.size)
        }
    }
}
