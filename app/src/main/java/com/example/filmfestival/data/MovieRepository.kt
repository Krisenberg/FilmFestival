package com.example.filmfestival.data

import androidx.compose.runtime.collectAsState
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.MovieAllData
import com.example.filmfestival.models.relations.RoleWithActor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
//
class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
){

//    val allMoviesOrderedByTitle: Flow<List<Movie>> = movieDao.getMoviesOrderedByTitle()

    suspend fun moviesIdPoster(): List<Pair<Int, String>> =
        movieDao.getMovies().map { movie: Movie ->
            Pair(movie.movieId, movie.moviePoster)
        }

    suspend fun movieAllData(movieId : Int): MovieAllData {
        val movieWithAwards = movieDao.getMovieWithAwards(movieId)
        val movieWithRoles = movieDao.getMovieWithRoles(movieId)
        val movieWithShows = movieDao.getMovieWithShows(movieId)
        val movieWithTrailers = movieDao.getMovieWithTrailers(movieId)
        return MovieAllData(
            movie = movieWithAwards.movie,
            awards = movieWithAwards.awards,
            rolesWithActors = movieWithRoles.roles
                .map { role -> movieDao.getRoleWithActor(role.roleId) },
            shows = movieWithShows.shows,
            trailers = movieWithTrailers.trailers
        )
    }


//    suspend fun moviesAllData(movieId: Int) {
//        val movieWithActors = movieDao.getMoviesWithActorsAndStarring(movieId)
//        movieWithActors.actors
//    }
}