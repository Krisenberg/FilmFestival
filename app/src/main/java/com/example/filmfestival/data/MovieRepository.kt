package com.example.filmfestival.data

import androidx.compose.runtime.collectAsState
import com.example.filmfestival.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val awardDao: AwardDao,
    private val castDao: CastDao,
    private val movieDao: MovieDao,
    private val showDao: ShowDao,
    private val ticketDao: TicketDao,
    private val trailerDao: TrailerDao,
    private val watchlistDao: WatchlistDao
){

    val allMoviesOrderedByTitle: Flow<List<Movie>> = movieDao.getMoviesOrderedByTitle()
//    val allMoviesData: List<MovieData>

//    private suspend fun getAllMoviesData(){
//        val movies = movieDao.getMoviesOrderedByTitle().collect()
//    }
}