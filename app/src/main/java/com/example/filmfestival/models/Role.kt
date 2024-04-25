package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Role")
data class Role(
    @PrimaryKey
    val roleId: Int,
    val movieId: Int,
    val actorId: Int,
    val starring: String
)