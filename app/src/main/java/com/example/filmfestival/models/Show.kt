package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(tableName = "Show",
//    foreignKeys = [
//        ForeignKey(
//            entity = Movie::class,
//            parentColumns = ["id"],
//            childColumns = ["movieId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "Show")
data class Show(
    @PrimaryKey
    val showId: Int,
    val movieId: Int,
    val dateTime: String
)