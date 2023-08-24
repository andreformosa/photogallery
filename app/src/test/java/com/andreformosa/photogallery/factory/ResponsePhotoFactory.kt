package com.andreformosa.photogallery.factory

import com.andreformosa.photogallery.data.model.remote.Photo
import kotlin.random.Random

fun createResponsePhoto(
    id: Int = Random.nextInt(),
    albumId: Int = Random.nextInt(),
    title: String = "title",
    url: String = "url",
    thumbnailUrl: String = "thumbnailUrl"
) = Photo(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)
