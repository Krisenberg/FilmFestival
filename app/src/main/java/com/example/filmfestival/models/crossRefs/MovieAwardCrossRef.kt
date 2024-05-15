package com.example.filmfestival.models.crossRefs

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie

@Entity(
    tableName = "MovieAwardCrossRef",
    primaryKeys = ["movieId", "awardId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = Award::class,
            parentColumns = ["awardId"],
            childColumns = ["awardId"]
        )
    ]
)
data class MovieAwardCrossRef (
    val movieId: Int,
    val awardId: Int
)