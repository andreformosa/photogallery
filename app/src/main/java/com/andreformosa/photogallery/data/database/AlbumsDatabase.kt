package com.andreformosa.photogallery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreformosa.photogallery.data.albums.AlbumDao
import com.andreformosa.photogallery.data.albums.PhotoDao
import com.andreformosa.photogallery.data.model.local.Album
import com.andreformosa.photogallery.data.model.local.Photo

@Database(
    entities = [Album::class, Photo::class],
    version = 1,
    exportSchema = false
)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
}
