package com.andreformosa.photogallery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreformosa.photogallery.data.albums.AlbumDao
import com.andreformosa.photogallery.data.albums.PhotoDao
import com.andreformosa.photogallery.data.albums.RemoteKeyDao
import com.andreformosa.photogallery.data.model.local.Album
import com.andreformosa.photogallery.data.model.local.Photo
import com.andreformosa.photogallery.data.model.local.RemoteKey

@Database(
    entities = [Album::class, Photo::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
