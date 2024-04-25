package com.example.filmfestival.models

import com.example.filmfestival.models.relations.RoleWithActor

data class MovieAllData (
    val movie : Movie,
    val awards : List<Award>,
    val rolesWithActors : List<RoleWithActor>,
    val shows : List<Show>,
    val trailers : List<Trailer>
)