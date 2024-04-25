package com.example.filmfestival.models.crossRefs

import androidx.room.Entity

//@Entity(tableName = "Cast",
//    primaryKeys = ["movieId", "actorId"],
//    foreignKeys = [
//        ForeignKey(
//            entity = Movie::class,
//            parentColumns = ["id"],
//            childColumns = ["movieId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Actor::class,
//            parentColumns = ["id"],
//            childColumns = ["actorId"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ])
@Entity(tableName = "MovieRoleCrossRef", primaryKeys = ["movieId", "roleId"])
data class MovieRoleCrossRef(
    val movieId: Int,
    val roleId: Int
)