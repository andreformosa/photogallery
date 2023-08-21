package com.andreformosa.photogallery.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)
