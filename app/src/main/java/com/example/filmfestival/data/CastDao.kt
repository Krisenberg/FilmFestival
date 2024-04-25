//package com.example.filmfestival.data
//
//import androidx.room.Dao
//import androidx.room.Query
//import com.example.filmfestival.models.Actor
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface CastDao {
//    @Query("SELECT Actor.id, Actor.name, Actor.photo FROM `Cast` JOIN Actor ON `Cast`.actorId == Actor.id WHERE `Cast`.movieId == :movieId")
//    fun getActorsFromMovie(movieId: Int): Flow<List<Actor>>
//}