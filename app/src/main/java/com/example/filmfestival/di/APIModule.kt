package com.example.filmfestival.di

import com.example.filmfestival.api.FilmFestivalAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://6672b1626ca902ae11b15d93.mockapi.io/mobileapp/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFilmFestivalAPI(retrofit: Retrofit): FilmFestivalAPI {
        return retrofit.create(FilmFestivalAPI::class.java)
    }
}