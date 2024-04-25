package com.example.filmfestival.data

import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.Trailer

data class MovieData(
    val movie: Movie,
    val cast: List<Pair<Actor, String>>,
    val awards: List<Award>,
    val trailers: List<Trailer>,
    val shows: List<Show>
)