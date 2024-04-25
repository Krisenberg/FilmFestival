package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(tableName = "Trailer",
//    foreignKeys = [
//        ForeignKey(
//            entity = Movie::class,
//            parentColumns = ["id"],
//            childColumns = ["movieId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "Trailer")
data class Trailer(
    @PrimaryKey
    val trailerId: Int,
    val movieId: Int,
    val trailer: String
)