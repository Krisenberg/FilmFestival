package com.example.filmfestival.apiTest

import com.example.filmfestival.api.APIResponseConverter
import com.example.filmfestival.api.NewsPreview
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ApiResponseConverterTest {
    private lateinit var mockNewsPreviews: List<NewsPreview>

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
}