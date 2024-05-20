package com.example.filmfestival.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef
import com.example.filmfestival.models.relations.UserWithWatchlistMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Upsert
    suspend fun upsertWatchlistEntry(entry: WatchlistCrossRef)

    @Delete
    suspend fun deleteWatchlistEntry(entry: WatchlistCrossRef)
}