package com.example.filmfestival.models.crossRefs

import androidx.room.Entity

//@Entity(tableName = "Ticket",
//    primaryKeys = ["userId", "showId"],
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["id"],
//            childColumns = ["userId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Show::class,
//            parentColumns = ["id"],
//            childColumns = ["showId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "TicketCrossRef", primaryKeys = ["userId", "showId"])
data class TicketCrossRef(
    val userId: Int,
    val showId: Int
)