package com.example.sportynews

import com.example.sportynews.data.dtos.ArticleDto
import com.example.sportynews.data.dtos.NewsResponseDto
import com.example.sportynews.data.dtos.SourceDto
import com.example.sportynews.data.mapper.toDomain
import com.example.sportynews.data.repositories.NewsRepository
import com.example.sportynews.domain.models.NewsArticle

class FakeNewsRepository : NewsRepository {
    var shouldFail = false
    override suspend fun getTopHeadlines(page: Int): Result<List<NewsArticle>> {
        return if (shouldFail) {
            Result.failure(Exception("Something went wrong"))
        } else {
            val response = NewsResponseDto(
                status = "ok",
                totalResults = 1,
                articles = listOf(
                    ArticleDto(
                        source = SourceDto(id = null, name = ""),
                        author = "",
                        title = "Test Title",
                        url = "",
                        content = "",
                        description = "Test Desc",
                        urlToImage = null,
                        publishedAt = "",
                    )
                )
            )
            Result.success(response.articles.map { it.toDomain() })
        }
    }

}