package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.filmfestival.models.relations.MovieWithRoles
import com.example.filmfestival.models.relations.MovieWithAwards

@Dao
interface FilmFestivalDao {

    @Transaction
    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithActors(movieId: Int): List<MovieWithRoles>

    @Transaction
    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithAwards(movieId: Int): List<MovieWithAwards>
}