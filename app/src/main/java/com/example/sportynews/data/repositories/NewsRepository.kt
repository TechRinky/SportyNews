package com.example.sportynews.data.repositories

import com.example.sportynews.domain.models.NewsArticle

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int):
            Result<List<NewsArticle>>
}