package com.example.filmfestival.api

import retrofit2.http.GET
import retrofit2.http.Path

interface FilmFestivalAPI {
    @GET("news")
    suspend fun getNewsPreview(): List<NewsPreview>

    @GET("news/{newsID}")
    suspend fun getNewsDetailsById(@Path("newsID") newsID: String): News
}