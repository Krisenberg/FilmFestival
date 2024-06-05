package com.example.filmfestival.data

import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.dto.MovieAllData
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
){

    suspend fun getMovieById(movieId: Int) = movieDao.getMovie(movieId)

    suspend fun moviesIdTitlePoster(): List<Triple<Int, String, String>> =
        movieDao.getMovies().map { movie: Movie ->
            Triple(movie.movieId, movie.title, movie.moviePoster)
        }

    suspend fun actorsIdPhoto(): List<Pair<Int, String>> =
        movieDao.getActors().map {actor: Actor ->
            Pair(actor.actorId, actor.photo)
        }

    suspend fun movieAllData(movieId : Int): MovieAllData {
        val movieWithAwards = movieDao.getMovieWithAwards(movieId)
        val movieWithRoles = movieDao.getMovieWithRoles(movieId)
        val movieWithShows = movieDao.getMovieWithShows(movieId)
        val movieWithTrailers = movieDao.getMovieWithTrailers(movieId)
        return MovieAllData(
            movie = movieWithAwards.movie,
            awards = movieWithAwards.awards,
            rolesWithActors = movieWithRoles.roles.map {
                role -> movieDao.getRoleWithActor(role.roleId)
            },
            shows = movieWithShows.shows,
            trailers = movieWithTrailers.trailers
        )
    }
}