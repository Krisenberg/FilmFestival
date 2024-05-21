package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.relations.MovieWithRoles
import com.example.filmfestival.models.relations.MovieWithAwards
import com.example.filmfestival.models.relations.MovieWithShows
import com.example.filmfestival.models.relations.MovieWithTrailers
import com.example.filmfestival.models.relations.RoleWithActor

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM Movie where movieId = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query("SELECT * FROM Actor")
    suspend fun getActors(): List<Actor>

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithAwards(movieId: Int): MovieWithAwards

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithRoles(movieId: Int): MovieWithRoles

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithShows(movieId: Int): MovieWithShows

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    suspend fun getMovieWithTrailers(movieId: Int): MovieWithTrailers

    @Query("SELECT * FROM Role WHERE roleId = :roleId")
    suspend fun getRoleWithActor(roleId: Int): RoleWithActor

}