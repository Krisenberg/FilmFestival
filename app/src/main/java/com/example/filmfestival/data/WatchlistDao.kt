package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef

@Dao
interface WatchlistDao {
    @Upsert
    suspend fun upsertWatchlistEntry(entry: WatchlistCrossRef)

    @Delete
    suspend fun removeWatchlistEntry(entry: WatchlistCrossRef)
}