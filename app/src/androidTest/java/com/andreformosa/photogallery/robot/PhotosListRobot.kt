package com.andreformosa.photogallery.robot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.andreformosa.photogallery.utils.waitUntilShown
import org.junit.rules.TestRule

class PhotosListRobot<R : TestRule, A : ComponentActivity>(private val testRule: AndroidComposeTestRule<R, A>) {

    fun clickFirstPhoto() {
        testRule.waitUntilShown {
            onNodeWithTag("photosList")
        }.onChildren()
            .onFirst()
            .performClick()
    }
}
