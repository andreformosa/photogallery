package com.andreformosa.photogallery.robot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import org.junit.rules.TestRule

class Robot<R : TestRule, A : ComponentActivity>(private val testRule: AndroidComposeTestRule<R, A>) {

    fun albumsListRobot(action: @RestrictScope AlbumsListRobot<R, A>.() -> Unit) =
        AlbumsListRobot(testRule).action()

    fun photosListRobot(action: @RestrictScope PhotosListRobot<R, A>.() -> Unit) =
        PhotosListRobot(testRule).action()
}

fun <R : TestRule, A : ComponentActivity> robot(
    testRule: AndroidComposeTestRule<R, A>,
    action: Robot<R, A>.() -> Unit,
) = Robot(testRule).action()
