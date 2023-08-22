package com.andreformosa.photogallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreformosa.photogallery.Destinations.ALBUMS_ROUTE
import com.andreformosa.photogallery.feature.album.AlbumsScreen

object Destinations {
    const val ALBUMS_ROUTE = "albums"
}

@Composable
fun PhotoGalleryNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = ALBUMS_ROUTE) {
        composable(ALBUMS_ROUTE) {
            AlbumsScreen()
        }
    }
}
