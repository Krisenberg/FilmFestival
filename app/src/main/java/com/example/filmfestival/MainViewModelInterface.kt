package com.example.filmfestival

import com.example.filmfestival.api.News
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.utils.Sound
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface MainViewModelInterface {
    suspend fun getMoviesIdTitlePoster(): List<Triple<Int, String, String>>
    suspend fun getActorsIdPhoto(): List<Pair<Int, String>>
    suspend fun getMovieAllData(movieId: Int): MovieAllData
    fun groupShowsByDate(shows: List<Show>): Map<LocalDate, List<Pair<LocalTime, Show>>>
    fun getUsersMovieTickets(userId: Int, movieId: Int): Flow<List<Show>>
    fun getUsersTickets(userId: Int): Flow<List<Triple<Movie, LocalDateTime, Show>>>
    suspend fun addUsersTicket(userId: Int, showId: Int)
    suspend fun removeUsersTicket(userId: Int, showId: Int)
    suspend fun getUserWatchlistMovies(userId: Int): List<Movie>
    suspend fun checkIfMovieIsOnUsersWatchlist(userId: Int, movieId: Int): Boolean
    suspend fun addMovieToUsersWatchlist(userId: Int, movieId: Int)
    suspend fun removeMovieFromUsersWatchlist(userId: Int, movieId: Int)
    fun playSound(sound: Sound)
    fun getUsername(userId: Int): Flow<String>
    fun getAvatar(userId: Int): Flow<String>
    fun changeUsername(userId: Int, newUsername: String)
    fun changeAvatar(userId: Int, newAvatar: String)
    suspend fun getFilmFestivalNewsPreviews(): List<NewsPreview>?
    suspend fun getFilmFestivalNewsDetailsById(newsId: String): News?
}