package com.example.filmfestival.api

import javax.inject.Inject

class NewsRepository @Inject constructor (
    private val api: FilmFestivalAPI
){

    suspend fun getNewsPreviews(): List<NewsPreview>? {
        var newsPreviewList: List<NewsPreview>? = null

        try {
            newsPreviewList = api.getNewsPreview()
        } catch (e: Exception){
            e.printStackTrace()
        }

        if (newsPreviewList != null)
            return APIResponseConverter.convertResponseNewsList(newsPreviewList)
        return null
    }

    suspend fun getNewsDetailsById(newsId: String): News? {
        var newsDetails: News? = null

        try {
            newsDetails = api.getNewsDetailsById(newsId)
        } catch (e: Exception){
            e.printStackTrace()
        }

        if (newsDetails != null)
            return APIResponseConverter.convertResponseSingleNews(newsDetails)
        return null
    }

}