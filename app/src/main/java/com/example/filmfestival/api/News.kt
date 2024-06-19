package com.example.filmfestival.api

import java.time.LocalDateTime

data class News (
    val imageUrl: String,
    val date: String,
    val title: String,
    val description: String,
    val id: String
)