package com.example.filmfestival.models.crossRefs

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.User

@Entity(
    tableName = "TicketCrossRef",
    primaryKeys = ["userId", "showId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Show::class,
            parentColumns = ["showId"],
            childColumns = ["showId"]
        )
    ]
)
data class TicketCrossRef(
    val userId: Int,
    val showId: Int
)