package com.example.filmfestival

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestival.data.MovieDao
import com.example.filmfestival.data.MovieRepository
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.MovieAllData
import com.example.filmfestival.models.crossRefs.MovieRoleCrossRef
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRepository: MovieRepository
): ViewModel() {

//    private val _actors = MutableLiveData<List<Actor>>()
//    val actors: LiveData<List<Actor>> = _actors

//    val moviesOrderedByTitle: Flow<List<Movie>> = movieRepository.allMoviesOrderedByTitle
    val moviesIdPoster = viewModelScope.async {
        movieRepository.moviesIdPoster()
    }

    suspend fun getMovieAllData(movieId: Int) = movieRepository.movieAllData(movieId)

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