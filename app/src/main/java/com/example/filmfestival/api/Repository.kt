package com.example.filmfestival.api

import javax.inject.Inject

//interface NewsRepository {
//    suspend fun getNewsPreviews(): List<NewsPreview>
//    suspend fun getNewsDetailsById(newsId: String): News
//}

class NewsRepository @Inject constructor (
    private val api: FilmFestivalAPI
){

    suspend fun getNewsPreviews(): List<NewsPreview> {
        var newsPreviewList: List<NewsPreview>? = null

        try {
            newsPreviewList = api.getNewsPreview()
        } catch (e: Exception){
            e.printStackTrace()
        }

        if (newsPreviewList == null)
            return listOf()

        return APIResponseConverter.convertResponseNewsList(newsPreviewList)
    }

    suspend fun getNewsDetailsById(newsId: String): News {
        var newsDetails: News? = null

        try {
            newsDetails = api.getNewsDetailsById(newsId)
        } catch (e: Exception){
            e.printStackTrace()
        }

        return APIResponseConverter.convertResponseSingleNews(newsDetails!!)
    }

}