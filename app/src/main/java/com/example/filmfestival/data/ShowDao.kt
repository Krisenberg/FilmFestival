//package com.example.filmfestival.data
//
//import androidx.room.Dao
//import androidx.room.Query
//import com.example.filmfestival.models.Show
//import com.example.filmfestival.models.Trailer
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface ShowDao {
//    @Query("SELECT * FROM Show WHERE Show.movieId == :movieId")
//    fun getShowsOfMovie(movieId: Int): Flow<List<Show>>
//}