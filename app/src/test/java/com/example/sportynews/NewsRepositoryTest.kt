package com.example.sportynews

import com.example.sportynews.data.ApiService
import com.example.sportynews.data.repositories.NewsRepository
import com.example.sportynews.data.repositories.NewsRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiService
    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        repository = NewsRepositoryImpl(api)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getNews should return success when API responds correctly`() = runTest {
        val mockResponse = MockResponse()
            .setBody("""
        {
            "status": "ok",
            "totalResults": 1,
            "articles": [
                {
                    "title": "Breaking News",
                    "description": "Something happened",
                    "url": "https://example.com",
                    "urlToImage": "https://example.com/image.jpg"
                }
            ]
        }
    """.trimIndent())
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val result = repository.getTopHeadlines(1)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.first()?.title).isEqualTo("Breaking News")
    }
}