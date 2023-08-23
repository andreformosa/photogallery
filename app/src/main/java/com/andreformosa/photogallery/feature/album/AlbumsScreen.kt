package com.andreformosa.photogallery.feature.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.andreformosa.photogallery.R
import com.andreformosa.photogallery.data.model.local.AlbumWithPhotos
import com.andreformosa.photogallery.ui.composable.EmptyState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun AlbumsScreen(
    viewModel: AlbumsViewModel = hiltViewModel(),
    onNavigateToPhotos: (albumId: Int) -> Unit
) {
    val albumsWithPhotos = viewModel.albumsWithPhotos.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        AlbumsList(
            albumsWithPhotos = albumsWithPhotos,
            onItemClick = { onNavigateToPhotos(it) }
        )

        // Note: Paging library seems to be limited as there does not
        // seem to be a way to delegate this logic to the ViewModel.
        // Thus, it unfortunately has to live within the view layer.
        if (albumsWithPhotos.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (albumsWithPhotos.loadState.refresh is LoadState.Error) {
            // Showing empty state. In an actual app, the ideal flow would be
            // to retry fetching the data and if that fails, showing an error.
            EmptyState(
                imageResource = R.drawable.ic_gallery_thumbnail,
                textResource = R.string.albums_screen_empty_state
            )
        } else if (albumsWithPhotos.itemCount == 0 &&
            albumsWithPhotos.loadState.append.endOfPaginationReached
        ) {
            // Handle empty state only when there are no ongoing loads and no error,
            // and if the append operation has reached the end of pagination.
            // This prevents briefly showing the empty state before actual data is loaded.
            EmptyState(
                imageResource = R.drawable.ic_gallery_thumbnail,
                textResource = R.string.albums_screen_empty_state
            )
        }
    }
}

@Composable
private fun AlbumsList(
    albumsWithPhotos: LazyPagingItems<AlbumWithPhotos>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = albumsWithPhotos.itemCount,
            key = albumsWithPhotos.itemKey { it.album.id },
        ) { index ->
            albumsWithPhotos[index]?.let { albumWithPhotos ->
                AlbumListItem(
                    albumWithPhotos = albumWithPhotos,
                    onClick = onItemClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumListItem(
    albumWithPhotos: AlbumWithPhotos,
    onClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            onClick = { onClick(albumWithPhotos.album.id) }
        ) {
            var showShimmer by remember { mutableStateOf(true) }
            AsyncImage(
                model = albumWithPhotos.photos.first().thumbnailUrl,
                contentScale = ContentScale.Crop,
                contentDescription = albumWithPhotos.album.title,
                modifier = Modifier
                    .aspectRatio(1f)
                    .placeholder(
                        visible = showShimmer,
                        color = MaterialTheme.colorScheme.secondary,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                onSuccess = { showShimmer = false }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = albumWithPhotos.album.title,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            overflow = TextOverflow.Clip
        )
    }
}
