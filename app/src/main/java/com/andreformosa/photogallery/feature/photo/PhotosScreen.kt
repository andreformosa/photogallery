package com.andreformosa.photogallery.feature.photo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andreformosa.photogallery.data.model.local.Photo
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    onNavigateToPhotoDetails: (photoId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        PhotosUiState.Loading -> {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PhotosUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PhotosList(
                    photos = (uiState as PhotosUiState.Success).photos,
                    onItemClick = { onNavigateToPhotoDetails(it) }
                )
            }
        }

        PhotosUiState.EmptyOrErrorState -> {
            // TODO: Show empty/error state
        }
    }
}

@Composable
private fun PhotosList(
    photos: List<Photo>,
    onItemClick: (photoId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(
                items = photos,
                key = { photo -> photo.id }
            ) { photo ->
                PhotoListItem(
                    photo = photo,
                    onClick = onItemClick
                )
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun PhotoListItem(
    photo: Photo,
    onClick: (photoId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showShimmer by remember { mutableStateOf(true) }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo.thumbnailUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = photo.title,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                enabled = true,
                onClick = { onClick(photo.id) }
            )
            .placeholder(
                visible = showShimmer,
                color = MaterialTheme.colorScheme.secondary,
                highlight = PlaceholderHighlight.shimmer()
            ),
        onSuccess = { showShimmer = false }
    )
}
