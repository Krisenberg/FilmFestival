package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Query
import com.example.filmfestival.models.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query("SELECT Show.id, Show.movieId, Show.dateTime FROM Ticket JOIN Show ON Ticket.showId == Show.id WHERE Ticket.userId == :userId")
    fun getShowsOfUser(userId: Int): Flow<List<Show>>
}