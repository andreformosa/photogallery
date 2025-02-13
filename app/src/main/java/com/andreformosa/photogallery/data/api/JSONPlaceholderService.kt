package com.andreformosa.photogallery.data.api

import com.andreformosa.photogallery.data.model.remote.Album
import com.andreformosa.photogallery.data.model.remote.Photo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JSONPlaceholderService {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("albums")
    suspend fun getAlbums(
        @Query("_page") page: Int?
    ): ApiResponse<List<Album>>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotosForAlbum(@Path("albumId") albumId: Int): ApiResponse<List<Photo>>
}
