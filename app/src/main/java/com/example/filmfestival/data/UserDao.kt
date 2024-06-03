package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.User
import com.example.filmfestival.models.relations.UserWithShows
import com.example.filmfestival.models.relations.UserWithWatchlistMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUserWithWatchlistMovies(userId: Int): UserWithWatchlistMovies

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserWithShows(userId: Int): Flow<UserWithShows>

    @Query("UPDATE User SET username = :newUsername WHERE userId = :userId")
    suspend fun updateUsername(userId: Int, newUsername: String)

    @Query("SELECT username FROM User WHERE userId = :userId")
    fun getUsername(userId: Int): Flow<String>
}