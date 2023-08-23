package com.andreformosa.photogallery.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "albumId")
    val albumId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String
)
