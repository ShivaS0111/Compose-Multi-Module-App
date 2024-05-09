package com.example.test_movie_app.di

import com.invia.data.datasource.network.ApiServiceImpl
import com.invia.domain.datasource.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging) // <-- This is the important line!
        // Add other interceptors or configurations as needed
        .build()


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
/*        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/").client(client).addConverterFactory(
            GsonConverterFactory.create()).build()*/
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiServiceImpl::class.java)
    }
}