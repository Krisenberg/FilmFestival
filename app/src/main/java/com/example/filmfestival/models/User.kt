package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    val userId: Int,
    val username: String,
    val avatar: String
)