package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Trailer

data class MovieWithTrailers (
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val trailers: List<Trailer>
)