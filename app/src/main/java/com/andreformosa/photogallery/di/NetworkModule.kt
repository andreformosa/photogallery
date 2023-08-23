package com.andreformosa.photogallery.di

import android.content.Context
import com.andreformosa.photogallery.common.JsonParser
import com.andreformosa.photogallery.data.api.JSONPlaceholderService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val debugInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(debugInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        jsonParser: JsonParser
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(JSONPlaceholderService.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(jsonParser.json.asConverterFactory("application/json".toMediaType()))
    }

    @Singleton
    @Provides
    fun provideJSONPlaceholderService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): JSONPlaceholderService {
        return retrofit
            .client(okHttpClient)
            .build()
            .create(JSONPlaceholderService::class.java)
    }
}
