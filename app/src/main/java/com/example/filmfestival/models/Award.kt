package com.example.filmfestival.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(tableName = "Award",
//    foreignKeys = [
//        ForeignKey(
//            entity = Movie::class,
//            parentColumns = ["id"],
//            childColumns = ["movieId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "Award")
data class Award(
    @PrimaryKey
    val awardId: Int,
    val name: String,
    val details: String
)