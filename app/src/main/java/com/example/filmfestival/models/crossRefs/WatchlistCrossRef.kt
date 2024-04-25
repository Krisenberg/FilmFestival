package com.example.filmfestival.models.crossRefs

import androidx.room.Entity

//@Entity(tableName = "Watchlist",
//    primaryKeys = ["userId", "movieId"],
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["id"],
//            childColumns = ["userId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Movie::class,
//            parentColumns = ["id"],
//            childColumns = ["movieId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "WatchlistCrossRef", primaryKeys = ["userId", "movieId"])
data class WatchlistCrossRef(
    val userId: Int,
    val movieId: Int
)