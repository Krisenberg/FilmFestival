package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.relations.MovieWithAwards
import com.example.filmfestival.models.relations.RoleWithActor
import com.example.filmfestival.models.relations.UserWithWatchlistMovies

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUserWithWatchlistMovies(userId: Int): UserWithWatchlistMovies
}