package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.filmfestival.models.crossRefs.TicketCrossRef

@Dao
interface TicketDao {
    @Upsert
    suspend fun upsertTicket(ticket: TicketCrossRef)

    @Delete
    suspend fun removeTicket(ticket: TicketCrossRef)
}