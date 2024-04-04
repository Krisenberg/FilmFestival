package com.example.filmfestival.data

import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
){
    val allMoviesOrderedByTitle = movieDao.getMoviesOrderedByTitle()
}