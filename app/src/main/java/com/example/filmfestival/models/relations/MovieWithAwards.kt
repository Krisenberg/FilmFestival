package com.example.filmfestival.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.crossRefs.MovieAwardCrossRef

data class MovieWithAwards (
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "awardId",
        associateBy = Junction(MovieAwardCrossRef::class)
    )
    val awards: List<Award>
)