package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Trailer")
data class Trailer(
    @PrimaryKey
    val trailerId: Int,
    val movieId: Int,
    val trailer: String
)