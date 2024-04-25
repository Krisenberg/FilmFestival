package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.User
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef

data class UserWithWatchlistMovies (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "movieId",
        associateBy = Junction(WatchlistCrossRef::class)
    )
    val watchlistMovies: List<Movie>
)