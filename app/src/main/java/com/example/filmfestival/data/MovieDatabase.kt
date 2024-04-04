package com.example.filmfestival.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmfestival.data.MovieDao
import com.example.filmfestival.models.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}