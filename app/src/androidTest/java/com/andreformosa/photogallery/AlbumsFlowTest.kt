package com.andreformosa.photogallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreformosa.photogallery.data.albums.RemoteAlbumsDataSource
import com.andreformosa.photogallery.data.database.AlbumsDatabase
import com.andreformosa.photogallery.data.model.remote.asAlbumEntity
import com.andreformosa.photogallery.data.model.remote.asPhotoEntity
import com.andreformosa.photogallery.factory.createResponseAlbum
import com.andreformosa.photogallery.factory.createResponsePhoto
import com.andreformosa.photogallery.fake.FakeRemoteAlbumsDataSource
import com.andreformosa.photogallery.robot.robot
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
/**
 * This class tests flows (user journeys).
 * Dependencies such as [RemoteAlbumsDataSource] are replaced with fakes (test doubles)
 * so as to have more control and predictability over the emitted data.
 * Fakes also help to reduce flakyness while providing the benefit of the tests running
 * significantly faster since no network connection is required to run them.
 */
class AlbumsFlowTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var database: AlbumsDatabase

    private val remoteDataSource = FakeRemoteAlbumsDataSource()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun testAlbumsToPhotoDetailsHappyFlow() {
        runBlocking {
            setUpFakeData()
        }

        robot(composeTestRule) {
            albumsListRobot {
                clickFirstAlbum()
            }
            photosListRobot {
                clickFirstPhoto()
            }
        }

        // Assert that flow is finished since photo details screen is displayed
        composeTestRule.onNodeWithTag("photoTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("photo").assertIsDisplayed()
    }

    private suspend fun setUpFakeData() {
        // Create fake albums and insert in in-memory database
        remoteDataSource.sendAlbumsResponse(ApiResponse.Success(Response.success(createFakeAlbums())))

        val remoteAlbums = remoteDataSource.getAlbums(0)
        val albums = (remoteAlbums as ApiResponse.Success).data.map { it.asAlbumEntity(0) }
        database.albumDao().insertAll(albums)

        // Create fake photos for albums and insert in in-memory database
        remoteDataSource.sendPhotosResponse(ApiResponse.Success(Response.success(createFakePhotos())))
        albums.forEach { album ->
            val remotePhotos = remoteDataSource.getPhotosForAlbum(album.id)
            val albumPhotos = (remotePhotos as ApiResponse.Success).data
                .filter { it.albumId == album.id }
                .map { it.asPhotoEntity() }
            database.photoDao().insertAll(albumPhotos)
        }
    }

    private fun createFakeAlbums() = listOf(
        createResponseAlbum(id = 1),
        createResponseAlbum(id = 2),
        createResponseAlbum(id = 3),
    )

    private fun createFakePhotos() = listOf(
        createResponsePhoto(
            id = 1,
            albumId = 1,
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        ),
        createResponsePhoto(
            id = 51,
            albumId = 2,
            thumbnailUrl = "https://via.placeholder.com/150/8e973b"
        ),
        createResponsePhoto(
            id = 101,
            albumId = 3,
            thumbnailUrl = "https://via.placeholder.com/150/e743b"
        )
    )
}
