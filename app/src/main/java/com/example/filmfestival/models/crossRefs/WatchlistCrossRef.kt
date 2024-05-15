package com.example.filmfestival.models.crossRefs

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.User

@Entity(
    tableName = "WatchlistCrossRef",
    primaryKeys = ["userId", "movieId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"]
        )
    ]
)
data class WatchlistCrossRef(
    val userId: Int,
    val movieId: Int
)