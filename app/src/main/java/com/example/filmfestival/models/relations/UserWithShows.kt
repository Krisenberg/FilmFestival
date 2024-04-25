package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.crossRefs.TicketCrossRef
import com.example.filmfestival.models.User

data class UserWithShows (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "showId",
        associateBy = Junction(TicketCrossRef::class)
    )
    val shows: List<Show>
)