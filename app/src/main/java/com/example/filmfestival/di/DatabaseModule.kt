package com.example.filmfestival.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.filmfestival.MyApp
import com.example.filmfestival.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movies_db"
    ).createFromAsset("database/movies.db").build()

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

}