package com.example.sportynews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportynews.domain.models.NewsArticle
import com.example.sportynews.domain.useCase.GetNewsHeadlinesUseCase
import com.example.sportynews.sealedclasses.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState : StateFlow<NewsUiState> = _uiState


    private val currentArticles = mutableListOf<NewsArticle>()
    private var currentPage = 1
    private var isLoadingMore = false
    private var isLastPage = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoadingMore || isLastPage) return

        isLoadingMore = true
        _uiState.value = NewsUiState.Loading

        viewModelScope.launch {
            val result = getNewsHeadlinesUseCase(
                page = currentPage,
            )

            result.onSuccess { newsResponse ->
                if (newsResponse.isEmpty()) {
                    isLastPage = true
                } else {
                    currentArticles.addAll(newsResponse)
                    _uiState.value = NewsUiState.Success(currentArticles)
                    currentPage++
                }
            }.onFailure { throwable ->
                _uiState.value = NewsUiState.Error(throwable.localizedMessage ?: "Unknown error")
            }
            isLoadingMore = false
        }
    }
}