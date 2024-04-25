//package com.example.filmfestival.data
//
//import androidx.room.Dao
//import androidx.room.Query
//import com.example.filmfestival.models.Award
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface AwardDao {
//    @Query("SELECT * FROM Award WHERE Award.movieId == :movieId")
//    fun getAwardsOfMovie(movieId: Int): Flow<List<Award>>
//}