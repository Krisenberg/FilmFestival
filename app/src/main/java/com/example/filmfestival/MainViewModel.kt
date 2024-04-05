package com.example.filmfestival

import androidx.lifecycle.ViewModel
import com.example.filmfestival.data.MovieRepository
import com.example.filmfestival.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    movieRepository: MovieRepository
): ViewModel() {

    val moviesOrderedByTitle: Flow<List<Movie>> = movieRepository.allMoviesOrderedByTitle

}