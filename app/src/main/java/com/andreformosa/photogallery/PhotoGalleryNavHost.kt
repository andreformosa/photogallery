package com.andreformosa.photogallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreformosa.photogallery.Destinations.ALBUM_ROUTE
import com.andreformosa.photogallery.feature.album.AlbumScreen

object Destinations {
    const val ALBUM_ROUTE = "album"
}

@Composable
fun PhotoGalleryNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = ALBUM_ROUTE) {
        composable(ALBUM_ROUTE) {
            AlbumScreen()
        }
    }
}
