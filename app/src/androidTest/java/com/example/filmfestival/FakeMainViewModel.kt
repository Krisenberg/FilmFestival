package com.example.filmfestival

import android.util.Log
import com.example.filmfestival.api.News
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Role
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.Trailer
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.models.relations.RoleWithActor
import com.example.filmfestival.utils.Sound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FakeMainViewModel : MainViewModelInterface {
    private val fakeMoviesIdTitlePoster = listOf(
        Triple(1, "Title1", "Poster1"),
        Triple(2, "Title2", "Poster2")
    )
    private val fakeActors = listOf(
        Actor(1, "Timothée Chalamet", "https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg"),
        Actor(2, "Zendaya", "https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg")
    )
    private val fakeActorsIdPhoto = listOf(
        Pair(1, "https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg"),
        Pair(2, "https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg")
    )
    private val fakeMovie = Movie(
        movieId = 1,
        title = "Fake Movie",
        moviePoster = "poster.jpg",
        moviePhoto = "photo.jpg",
        year = 2022,
        duration = 120,
        genre = "Action",
        description = "A fake movie description."
    )
    private val fakeAwards = listOf(
        Award(1, "Best Picture", "2023"),
        Award(2, "Best Actor", "2023")
    )
    private val fakeRolesWithActors = listOf(
        RoleWithActor(Role(1, 1, 1, "Protagonist"), Actor(1, "John Doe", "john.jpg")),
        RoleWithActor(Role(2, 1, 2, "Antagonist"), Actor(2, "Jane Doe", "jane.jpg"))
    )
    private val fakeShows = listOf(
        Show(1, 1, "2023-01-01T18:00:00"),
        Show(2, 2, "2023-01-02T18:00:00")
    )
    private val fakeTrailers = listOf(
        Trailer(1, 1 ,"https://youtube.com/trailer1"),
        Trailer(2, 1, "https://youtube.com/trailer2")
    )
    private val fakeMovieAllData = MovieAllData(
        movie = fakeMovie,
        awards = fakeAwards,
        rolesWithActors = fakeRolesWithActors,
        shows = fakeShows,
        trailers = fakeTrailers
    )
    private val fakeMovies = listOf(
        Movie(1, "Movie 1", "poster1.jpg", "photo1.jpg", 2023, 130, "Drama", "Description 1"),
        Movie(2, "Movie 2", "poster2.jpg", "photo2.jpg", 2023, 110, "Comedy", "Description 2")
    )
    private val fakeUserTickets: Flow<List<Triple<Movie, LocalDateTime, Show>>> = flowOf(
        listOf(
            Triple(fakeMovies[0], LocalDateTime.now(), fakeShows[0]),
            Triple(fakeMovies[1], LocalDateTime.now(), fakeShows[1])
        )
    )
    private val fakeUserWatchlistMovies = listOf(
        Movie(3, "Movie 4", "poster1.jpg", "photo1.jpg", 2023, 130, "Drama", "Description 1"),
        Movie(4, "Movie 3", "poster2.jpg", "photo2.jpg", 2023, 110, "Comedy", "Description 2")
    )

    var removeMovieFromWatchlistCalled = false
    var removeUsersTicketCalled = false
    private var username = "oldUserName"
    private var avatarUrl = "https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg"

    override suspend fun getMoviesIdTitlePoster(): List<Triple<Int, String, String>> {
        return fakeMoviesIdTitlePoster
    }

    override suspend fun getActorsIdPhoto(): List<Pair<Int, String>> {
        return fakeActorsIdPhoto
    }

    override suspend fun getMovieAllData(movieId: Int): MovieAllData {
        return fakeMovieAllData
    }

    override fun groupShowsByDate(shows: List<Show>): Map<LocalDate, List<Pair<LocalTime, Show>>> {
        return emptyMap()
    }

    override fun getUsersMovieTickets(userId: Int, movieId: Int): Flow<List<Show>> {
        return flowOf(emptyList())
    }

    override fun getUsersTickets(userId: Int): Flow<List<Triple<Movie, LocalDateTime, Show>>> {
        return fakeUserTickets
    }

    override suspend fun addUsersTicket(userId: Int, showId: Int) {
    }

    override suspend fun removeUsersTicket(userId: Int, showId: Int) {
        removeUsersTicketCalled = true
        Log.d("FakeMainViewModel", "removeUsersTicketCalled: $removeMovieFromWatchlistCalled")
    }

    override suspend fun getUserWatchlistMovies(userId: Int): List<Movie> {
        return fakeUserWatchlistMovies
    }

    override suspend fun checkIfMovieIsOnUsersWatchlist(userId: Int, movieId: Int): Boolean {
        return false
    }

    override suspend fun addMovieToUsersWatchlist(userId: Int, movieId: Int) {
    }

    override suspend fun removeMovieFromUsersWatchlist(userId: Int, movieId: Int) {
        removeMovieFromWatchlistCalled = true
//        Log.d("FakeMainViewModel", "removeMovieFromWatchlistCalled: $removeMovieFromWatchlistCalled")
    }

    override fun playSound(sound: Sound) {
    }

    override fun getUsername(userId: Int): Flow<String> {
        return flowOf(username)
    }

    override fun getAvatar(userId: Int): Flow<String> {
        return flowOf(avatarUrl)
    }

    override fun changeUsername(userId: Int, newUsername: String) {
        username = newUsername
    }

    override fun changeAvatar(userId: Int, newAvatar: String) {
        avatarUrl = newAvatar
    }

    override suspend fun getFilmFestivalNewsPreviews(): List<NewsPreview>? { return null; }

    override suspend fun getFilmFestivalNewsDetailsById(newsId: String): News? { return null; }
}
