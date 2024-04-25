package com.example.filmfestival.models.crossRefs

import androidx.room.Entity

@Entity(tableName = "MovieAwardCrossRef", primaryKeys = ["movieId", "awardId"])
data class MovieAwardCrossRef (
    val movieId: Int,
    val awardId: Int
)