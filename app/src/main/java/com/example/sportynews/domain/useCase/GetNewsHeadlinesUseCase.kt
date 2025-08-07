package com.example.sportynews.domain.useCase


import com.example.sportynews.data.repositories.NewsRepository
import com.example.sportynews.domain.models.NewsArticle
import javax.inject.Inject

class GetNewsHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(page: Int):
            Result<List<NewsArticle>> {
        return repository.getTopHeadlines(page)
    }
}