package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT Movie.id, Movie.title, Movie.moviePoster, Movie.moviePhoto, " +
            "Movie.year, Movie.duration, Movie.genre, Movie.description " +
            "FROM Watchlist JOIN Movie ON Watchlist.movieId == Movie.id WHERE Watchlist.userId == :userId")
    fun getMoviesOfUser(userId: Int): Flow<List<Movie>>
}