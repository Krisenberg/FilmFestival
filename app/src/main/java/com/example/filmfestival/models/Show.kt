package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Show")
data class Show(
    @PrimaryKey
    val showId: Int,
    val movieId: Int,
    val dateTime: String
)