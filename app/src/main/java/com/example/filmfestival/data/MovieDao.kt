package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.crossRefs.MovieRoleCrossRef
import com.example.filmfestival.models.relations.MovieWithRoles
import com.example.filmfestival.models.relations.MovieWithAwards
import com.example.filmfestival.models.relations.MovieWithShows
import com.example.filmfestival.models.relations.MovieWithTrailers
import com.example.filmfestival.models.relations.RoleWithActor

@Dao
interface MovieDao {

//    @Upsert
//    suspend fun addMovie(movie: Movie)
//
//    @Upsert
//    suspend fun addActor(actor: Actor)
//
//    @Upsert
//    suspend fun addMovieCast(movieCast: MovieRoleCrossRef)

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<Movie>

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


//    @Transaction
//    @Query("SELECT Movie.*, `Cast`.starring FROM Movie INNER JOIN `Cast` ON Movie.movieId = `Cast`.movieId INNER JOIN Actor ON `Cast`.actorId = Actor.actorId")
//    suspend fun getMovieWithActors(movieId: Int): MovieWithActors
//    @Transaction
//    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
//    fun getMoviesWithActorsAndStarring(movieId: Int): MovieWithRoles
//
//    @Transaction
//    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
//    suspend fun getMovieWithAwards(movieId: Int): MovieWithAwards
//
//    @Transaction
//    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
//    suspend fun getMovieWithTrailers(movieId: Int): MovieWithTrailers
//
//    @Transaction
//    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
//    suspend fun getMovieWithShows(movieId: Int): MovieWithShows
}