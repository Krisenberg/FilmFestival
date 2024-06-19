package com.example.filmfestival.apiTest

import com.example.filmfestival.api.APIResponseConverter
import com.example.filmfestival.api.FilmFestivalAPI
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.api.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ApiCallTest {
    private val api: FilmFestivalAPI = mock(FilmFestivalAPI::class.java)
    private lateinit var newsRepository: NewsRepository
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        newsRepository = NewsRepository(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getNewsPreviews with successful API response`() = runTest {
        val mockNewsPreviews = listOf(
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

        whenever(api.getNewsPreview()).thenReturn(mockNewsPreviews)

        val result = newsRepository.getNewsPreviews()
        val expected = APIResponseConverter.convertResponseNewsList(mockNewsPreviews)

        assertEquals(expected, result)
        verify(api, times(1)).getNewsPreview()
    }

    @Test
    fun `test getNewsPreviews with API exception`() = runTest {
        whenever(api.getNewsPreview()).thenThrow(RuntimeException("API call failed"))

        val result = newsRepository.getNewsPreviews()

        assertEquals(null, result)
        verify(api, times(1)).getNewsPreview()
    }
}
