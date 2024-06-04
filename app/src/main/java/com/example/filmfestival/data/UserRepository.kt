package com.example.filmfestival.data

import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.crossRefs.TicketCrossRef
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef
import com.example.filmfestival.models.relations.UserWithShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val movieDao: MovieDao,
    private val watchlistDao: WatchlistDao,
    private val showDao: ShowDao,
    private val ticketDao: TicketDao
){

//    val allMoviesOrderedByTitle: Flow<List<Movie>> = movieDao.getMoviesOrderedByTitle()

    fun getUsername(userId: Int): Flow<String> {
        return userDao.getUsername(userId)
    }
    fun getAvatar(userId: Int): Flow<String> {
        return userDao.getAvatar(userId)
    }
    suspend fun changeUsername(userId: Int, newUsername: String) {
        userDao.updateUsername(userId, newUsername)
    }

    suspend fun changeAvatar(userId: Int, newAvatar: String) {
        userDao.updateAvatar(userId, newAvatar)
    }
    suspend fun getUserWatchlistMovies(userId : Int): List<Movie> = userDao.getUserWithWatchlistMovies(userId).watchlistMovies

    suspend fun checkIfMovieIsOnUsersWatchlist(userId: Int, movieId: Int): Boolean {
        val movie = movieDao.getMovie(movieId)
        return userDao.getUserWithWatchlistMovies(userId).watchlistMovies.contains(movie)
    }

    suspend fun addToUsersWatchlist(userId: Int, movieId: Int) {
        val entry = WatchlistCrossRef(userId, movieId)
        watchlistDao.upsertWatchlistEntry(entry)
    }

    suspend fun removeFromUsersWatchlist(userId: Int, movieId: Int) {
        val entry = WatchlistCrossRef(userId, movieId)
        watchlistDao.removeWatchlistEntry(entry)
    }

    fun getUsersMovieTickets(userId: Int, movieId: Int): Flow<List<Show>> =
        userDao.getUserWithShows(userId).map{ value: UserWithShows ->
            value.shows.filter { show: Show -> show.movieId == movieId } }

    fun getUsersTickets(userId: Int): Flow<List<Show>> =
        userDao.getUserWithShows(userId).map{ value: UserWithShows -> value.shows }

    suspend fun addUserTicket(userId: Int, showId: Int) {
        val ticket = TicketCrossRef(userId, showId)
        ticketDao.upsertTicket(ticket)
    }

    suspend fun removeUsersTicket(userId: Int, showId: Int) {
        val ticket = TicketCrossRef(userId, showId)
        ticketDao.removeTicket(ticket)
    }
}