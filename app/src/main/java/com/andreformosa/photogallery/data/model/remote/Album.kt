package com.andreformosa.photogallery.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.andreformosa.photogallery.data.model.local.Album as AlbumEntity

@Serializable
data class Album(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)

fun Album.asAlbumEntity(page: Int) = AlbumEntity(
    id = id,
    title = title,
    page = page
)
