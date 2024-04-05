package com.example.filmfestival.data

import androidx.compose.runtime.collectAsState
import com.example.filmfestival.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
){
    val allMoviesOrderedByTitle: Flow<List<Movie>> = movieDao.getMoviesOrderedByTitle()
}