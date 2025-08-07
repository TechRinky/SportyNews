package com.example.sportynews.sealedclasses

import com.example.sportynews.domain.models.NewsArticle

sealed class NewsUiState {
    object Empty: NewsUiState()
    object Loading: NewsUiState()
    data class Success(val articles: List<NewsArticle>): NewsUiState()
    data class Error(val message: String): NewsUiState()

}