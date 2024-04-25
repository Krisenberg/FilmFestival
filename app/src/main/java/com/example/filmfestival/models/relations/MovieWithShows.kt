package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show

data class MovieWithShows (
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val shows: List<Show>
)