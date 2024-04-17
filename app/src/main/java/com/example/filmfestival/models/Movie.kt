package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val moviePoster: String,
    val moviePhoto: String,
    val year: Int,
    val duration: Int,
    val genre: String,
    val description: String
)
