package com.example.filmfestival.models.dto

import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.Trailer
import com.example.filmfestival.models.relations.RoleWithActor

data class MovieAllData (
    val movie : Movie,
    val awards : List<Award>,
    val rolesWithActors : List<RoleWithActor>,
    val shows : List<Show>,
    val trailers : List<Trailer>
)