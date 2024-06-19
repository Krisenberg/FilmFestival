package com.example.filmfestival.apiTest

import com.example.filmfestival.api.APIResponseConverter
import com.example.filmfestival.api.News
import com.example.filmfestival.api.NewsPreview
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ApiResponseConverterTest {
    private lateinit var mockNewsPreviews: List<NewsPreview>
    private lateinit var mockNewsDetails: News

    @Before
    fun setup() {
        mockNewsPreviews = listOf(
            NewsPreview(
                imageUrl = "image_url_1",
                date = "2024-06-16T16:48:25",
                title = "News_1",
                id = "1"
            ),
            NewsPreview(
                imageUrl = "image_url_2",
                date = "2024 06 16 16:48:25",
                title = "News_2",
                id = "2"
            )
        )

        mockNewsDetails = News(
            imageUrl = "image_url_1",
            date = "2024-06-1616:48:25",
            title = "News_1",
            description = "News 1 description",
            id = "1"
        )
    }

    @Test
    fun `Prepare datetime, correct datetime string`() {
        val convertedDate = APIResponseConverter.prepareDateTime(mockNewsPreviews[0].date)
        assert(convertedDate == "16 June 2024, 16:48")
    }

    @Test
    fun `Prepare datetime, incorrect datetime string`() {
        val convertedDate = APIResponseConverter.prepareDateTime(mockNewsPreviews[1].date)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.ENGLISH)
        assert(convertedDate == LocalDateTime.now().format(outputFormatter))
    }

    @Test
    fun `Test converting news previews list`() {
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.ENGLISH)
        val correctPreviews = listOf(
            NewsPreview(
                imageUrl = "image_url_1",
                date = "16 June 2024, 16:48",
                title = "News_1",
                id = "1"
            ),
            NewsPreview(
                imageUrl = "image_url_2",
                date = LocalDateTime.now().format(outputFormatter),
                title = "News_2",
                id = "2"
            )
        )
        val convertedPreviews = APIResponseConverter.convertResponseNewsList(mockNewsPreviews)
        assert(convertedPreviews == correctPreviews)
    }

    @Test
    fun `Test converting news details`() {
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.ENGLISH)
        val correctDetails = News(
            imageUrl = "image_url_1",
            date = LocalDateTime.now().format(outputFormatter),
            title = "News_1",
            description = "News 1 description",
            id = "1"
        )
        val convertedNews = APIResponseConverter.convertResponseSingleNews(mockNewsDetails)
        assert(convertedNews == correctDetails)
    }
}