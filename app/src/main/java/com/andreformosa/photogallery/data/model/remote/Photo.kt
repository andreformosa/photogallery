package com.andreformosa.photogallery.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.andreformosa.photogallery.data.model.local.Photo as PhotoEntity

@Serializable
data class Photo(
    @SerialName("albumId")
    val albumId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String
)

fun Photo.asPhotoEntity() = PhotoEntity(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)
