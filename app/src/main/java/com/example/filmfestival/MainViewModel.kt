package com.example.filmfestival

import androidx.lifecycle.ViewModel
import com.example.filmfestival.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    movieRepository: MovieRepository
): ViewModel() {

    val moviesOrderedByTitle = movieRepository.allMoviesOrderedByTitle

}