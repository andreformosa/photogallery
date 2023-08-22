package com.andreformosa.photogallery.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "albumId")
    val albumId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis()
)
