package com.example.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.TheMovieDbApi
import com.example.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun httpClient(@ApplicationContext context: Context)= OkHttpClient.Builder()
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(true)
                .build()
        )
        .build()

    @Provides
    @Singleton
    fun bindApi(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build().create(TheMovieDbApi::class.java)

    @Singleton
    @Provides
    fun provideMoviesRepository(
        movieDbApi: TheMovieDbApi
    ): com.example.domain.repository.MovieRepository {
        return MovieRepositoryImpl(movieDbApi)
    }
}