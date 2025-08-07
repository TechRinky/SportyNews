package com.example.sportynews.data.repositories

import com.example.sportynews.data.ApiService
import com.example.sportynews.data.mapper.toDomain
import com.example.sportynews.domain.models.NewsArticle
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService
): NewsRepository {

    override suspend fun getTopHeadlines(page: Int): Result<List<NewsArticle>> {
        return try {
            val response = api.getTopHeadlines(

                apiKey = "86178b7f9833438288f67c8d96440117"
            )
            Result.success(response.articles.map { it.toDomain() })
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}