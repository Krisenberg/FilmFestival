//package com.example.filmfestival.data
//
//import androidx.room.Dao
//import androidx.room.Query
//import com.example.filmfestival.models.Actor
//import com.example.filmfestival.models.Trailer
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface TrailerDao {
//    @Query("SELECT * FROM Trailer WHERE Trailer.movieId == :movieId")
//    fun getTrailersOfMovie(movieId: Int): Flow<List<Trailer>>
//}