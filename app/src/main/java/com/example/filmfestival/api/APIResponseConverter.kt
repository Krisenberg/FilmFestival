package com.example.filmfestival.api

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object APIResponseConverter {

    fun convertResponseNewsList(newsPreviewList: List<NewsPreview>): List<NewsPreview> {
        val news = newsPreviewList.map { news -> news.copy(date = prepareDateTime(news.date)) }
        return news
    }

    fun convertResponseSingleNews(news: News): News {
        return news.copy(date = prepareDateTime(news.date))
    }

    fun prepareDateTime(dateTimeString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.ENGLISH)

        return try {
            val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)
            dateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            LocalDateTime.now().format(outputFormatter)
        }
    }
}