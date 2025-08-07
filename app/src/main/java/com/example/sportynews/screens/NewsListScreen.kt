package com.example.sportynews.screens


import android.net.Uri

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sportynews.components.NewsListContent
import com.example.sportynews.presentation.NewsViewModel
import com.example.sportynews.utility.toJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    NewsListContent(
        state = state,
        onRetry = { viewModel.loadNextPage() },
        onNewsClick = { article ->
            val articleJson = Uri.encode(article.toJson())
            navController.navigate("news_detail/$articleJson")
        }
    )
}
