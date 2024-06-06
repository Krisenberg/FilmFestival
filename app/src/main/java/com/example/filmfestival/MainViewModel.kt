package com.example.filmfestival

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestival.data.MovieRepository
import com.example.filmfestival.data.UserRepository
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.utils.Sound
import com.example.filmfestival.utils.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository,
    private val soundManager: SoundManager
): ViewModel(), MainViewModelInterface {

    override suspend fun getMoviesIdTitlePoster() = movieRepository.moviesIdTitlePoster()
    override suspend fun getActorsIdPhoto() = movieRepository.actorsIdPhoto()
    override suspend fun getMovieAllData(movieId: Int) = movieRepository.movieAllData(movieId)

    override fun groupShowsByDate(shows: List<Show>): Map<LocalDate, List<Pair<LocalTime, Show>>> {
        return shows.groupBy {
            LocalDateTime.parse(it.dateTime, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
        }.mapValues { entry ->
            entry.value.map { show ->
                Pair(
                    LocalDateTime.parse(show.dateTime, DateTimeFormatter.ISO_DATE_TIME).toLocalTime(),
                    show
                )
            }
        }
    }

    override fun getUsersMovieTickets(userId: Int, movieId: Int) = userRepository.getUsersMovieTickets(userId, movieId)

    override fun getUsersTickets(userId: Int): Flow<List<Triple<Movie, LocalDateTime, Show>>> =
        userRepository.getUsersTickets(userId).map { shows: List<Show> ->
            shows.map { show: Show ->
                val movie = movieRepository.getMovieById(show.movieId)
                val showDateTime = LocalDateTime.parse(show.dateTime, DateTimeFormatter.ISO_DATE_TIME)
                Triple(movie, showDateTime, show)
            }
        }

    override suspend fun addUsersTicket(userId: Int, showId: Int) = userRepository.addUserTicket(userId, showId)
    override suspend fun removeUsersTicket(userId: Int, showId: Int) = userRepository.removeUsersTicket(userId, showId)
    override suspend fun getUserWatchlistMovies(userId: Int) = userRepository.getUserWatchlistMovies(userId)
    override suspend fun checkIfMovieIsOnUsersWatchlist(userId: Int, movieId: Int) = userRepository.checkIfMovieIsOnUsersWatchlist(userId, movieId)
    override suspend fun addMovieToUsersWatchlist(userId: Int, movieId: Int) = userRepository.addToUsersWatchlist(userId, movieId)
    override suspend fun removeMovieFromUsersWatchlist(userId: Int, movieId: Int) = userRepository.removeFromUsersWatchlist(userId, movieId)

    override fun playSound(sound: Sound) {
        soundManager.playSound(sound)
    }

    override fun getUsername(userId: Int): Flow<String> {
        return userRepository.getUsername(userId)
    }

    override fun getAvatar(userId: Int): Flow<String> {
        return userRepository.getAvatar(userId)
    }

    override fun changeUsername(userId: Int, newUsername: String) {
        viewModelScope.launch {
            userRepository.changeUsername(userId, newUsername)
        }
    }

    override fun changeAvatar(userId: Int, newAvatar: String) {
        viewModelScope.launch {
            userRepository.changeAvatar(userId, newAvatar)
        }
    }
}