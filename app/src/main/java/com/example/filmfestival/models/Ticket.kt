package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "Ticket",
    primaryKeys = ["userId", "showId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ])
data class Ticket(
    val userId: Int,
    val showId: Int
)