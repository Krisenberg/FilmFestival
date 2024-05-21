package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.Trailer
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {

    @Query("SELECT * FROM Show where showId = :showId")
    suspend fun getShow(showId: Int): Show

    @Query("SELECT * FROM Show WHERE movieId = :movieId ORDER BY dateTime")
    suspend fun getAllShowsOfMovie(movieId: Int): List<Show>
}