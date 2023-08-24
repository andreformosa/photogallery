package com.andreformosa.photogallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.andreformosa.photogallery.robot.robot
import org.junit.Rule
import org.junit.Test

/**
 * This class has end-to-end tests which test flows.
 * Such tests should be kept to a minimum since they are time consuming and more prone to being flaky.
 * A potential improvement would be to provide fake dependencies instead, thus making
 * the tests significantly faster while also reducing flaky behavior.
 */
class AlbumsFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAlbumsToPhotoDetailsHappyFlow() {
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
}
