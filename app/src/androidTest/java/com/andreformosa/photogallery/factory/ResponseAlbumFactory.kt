package com.andreformosa.photogallery.factory

import com.andreformosa.photogallery.data.model.remote.Album
import kotlin.random.Random

fun createResponseAlbum(
    id: Int = Random.nextInt(),
    title: String = "title",
) = Album(
    id = id,
    title = title
)
