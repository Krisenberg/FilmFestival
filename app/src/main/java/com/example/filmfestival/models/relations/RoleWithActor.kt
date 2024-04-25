package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Role

data class RoleWithActor (
    @Embedded val role: Role,
    @Relation(
        parentColumn = "actorId",
        entityColumn = "actorId"
    )
    val actor: Actor
)