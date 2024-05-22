package com.example.filmfestival

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.filmfestival.data.MovieDao
import com.example.filmfestival.data.MovieRepository
import com.example.filmfestival.data.UserRepository
import com.example.filmfestival.models.Show
import com.example.filmfestival.utils.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository
//    private val navController: NavController
): ViewModel() {

//    private val _actors = MutableLiveData<List<Actor>>()
//    val actors: LiveData<List<Actor>> = _actors

//    val moviesOrderedByTitle: Flow<List<Movie>> = movieRepository.allMoviesOrderedByTitle
//    val moviesIdPoster = viewModelScope.async {
//        movieRepository.moviesIdPoster()
//    }

//    fun navigateTo(navigationRoute: NavigationRoutes) {
//        navController.nav
//    }

    suspend fun getMoviesIdTitlePoster() = movieRepository.moviesIdTitlePoster()

    suspend fun getMovieAllData(movieId: Int) = movieRepository.movieAllData(movieId)

    fun groupShowsByDate(shows: List<Show>): Map<LocalDate, List<Pair<LocalTime, Show>>> {
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

    fun getUsersMovieTickets(userId: Int, movieId: Int) = userRepository.getUsersMovieTickets(userId, movieId)

    suspend fun addUsersTicket(userId: Int, showId: Int) = userRepository.addUserTicket(userId, showId)
    suspend fun removeUsersTicket(userId: Int, showId: Int) = userRepository.removeUsersTicket(userId, showId)

//    val actors = viewModelScope.async {
//        movieDao.getActors()
//    }

//    fun getActors(): Deferred<List<Actor>> {
////        var actors = listOf<Actor>()
//        return viewModelScope.async {
////            _actors.value = movieDao.getActors().await()
//            movieDao.getActors()
//        }
//    }
    suspend fun getActors() = movieDao.getActors()

    suspend fun getUserWatchlistMovies(userId: Int) = userRepository.getUserWatchlistMovies(userId)

    suspend fun checkIfMovieIsOnUsersWatchlist(userId: Int, movieId: Int) = userRepository.checkIfMovieIsOnUsersWatchlist(userId, movieId)
    suspend fun addMovieToUsersWatchlist(userId: Int, movieId: Int) = userRepository.addToUsersWatchlist(userId, movieId)
    suspend fun removeMovieFromUsersWatchlist(userId: Int, movieId: Int) = userRepository.removeFromUsersWatchlist(userId, movieId)

//    val movie = Movie(1,"Dune: Part Two","https://image.tmdb.org/t/p/original/5aUVLiqcW0kFTBfGsCWjvLas91w.jpg",
//        "https://ca-times.brightspotcdn.com/dims4/default/c04179b/2147483647/strip/true/crop/2700x1580+0+0/resize/1200x702!/quality/75/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2Fdc%2F0c%2F962ffd064d33a015a95c54b07c80%2Fdun2-27986r-jpv3.jpg",
//        2024,166,"Action",
//        "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.")
//    val actor = Actor(1,"Timothée Chalamet","https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg")
//    val cast = MovieRoleCrossRef(1,1,"Paul Atreides")

//    fun insertData() = viewModelScope.launch {
//        movieDao.addMovie(movie)
//        movieDao.addMovieCast(cast)
//        movieDao.addActor(actor)
//    }

//    fun getDataSample(): String {
//        val cast = movieDao.getMoviesWithActorsAndStarring(1).actorsWithRole
//        val text = cast[0].actor.name + " " + cast[0].starring
//        return text
//    }

//    viewModelScope.
//    view {
//        movieDao.addMovie(movie)
//        movieDao.addMovieCast(cast)
//        movieDao.addActor(actor)
//    }

}