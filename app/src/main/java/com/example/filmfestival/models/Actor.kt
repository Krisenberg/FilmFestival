package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Actor")
data class Actor(
    @PrimaryKey
    val actorId: Int,
    val name: String,
    val photo: String
)