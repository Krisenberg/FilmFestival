package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Award")
data class Award(
    @PrimaryKey
    val awardId: Int,
    val name: String,
    val details: String
)