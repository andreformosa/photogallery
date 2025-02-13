package com.andreformosa.photogallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreformosa.photogallery.Destinations.ALBUMS_ROUTE
import com.andreformosa.photogallery.Destinations.PHOTOS_ROUTE
import com.andreformosa.photogallery.Destinations.PHOTO_DETAILS_ROUTE
import com.andreformosa.photogallery.feature.album.AlbumsScreen
import com.andreformosa.photogallery.feature.detail.PhotoDetailsScreen
import com.andreformosa.photogallery.feature.photo.PhotosScreen

object Destinations {
    const val ALBUMS_ROUTE = "albums"
    const val PHOTOS_ROUTE = "photos/{albumId}"
    const val PHOTO_DETAILS_ROUTE = "details/{photoId}"
}

@Composable
fun PhotoGalleryNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = ALBUMS_ROUTE) {
        composable(ALBUMS_ROUTE) {
            AlbumsScreen(
                onNavigateToPhotos = { navController.navigate("photos/$it") }
            )
        }
        composable(
            route = PHOTOS_ROUTE,
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) {
            PhotosScreen(
                onNavigateToPhotoDetails = { navController.navigate("details/$it") }
            )
        }
        composable(
            route = PHOTO_DETAILS_ROUTE,
            arguments = listOf(navArgument("photoId") { type = NavType.IntType })
        ) {
            PhotoDetailsScreen()
        }
    }
}
