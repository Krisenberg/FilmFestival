package com.example.filmfestival.data

import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.models.relations.UserWithWatchlistMovies
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val watchlistDao: WatchlistDao
){

//    val allMoviesOrderedByTitle: Flow<List<Movie>> = movieDao.getMoviesOrderedByTitle()
    suspend fun getUserWatchlistMovies(userId : Int): List<Movie> = userDao.getUserWithWatchlistMovies(userId).watchlistMovies

    suspend fun addToUsersWatchlist(userId: Int, movieId: Int) {
        val entry = WatchlistCrossRef(userId, movieId)
        watchlistDao.upsertWatchlistEntry(entry)
    }

    suspend fun deleteFromUsersWatchlist(userId: Int, movieId: Int) {
        val entry = WatchlistCrossRef(userId, movieId)
        watchlistDao.deleteWatchlistEntry(entry)
    }
}