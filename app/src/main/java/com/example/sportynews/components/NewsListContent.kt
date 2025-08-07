package com.example.sportynews.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.sportynews.domain.models.NewsArticle
import com.example.sportynews.sealedclasses.NewsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListContent(
    state: NewsUiState,
    onRetry: () -> Unit = {},
    onNewsClick: (NewsArticle) -> Unit = {}
) {
    val listState = rememberLazyListState()

    when (state) {
        is NewsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is NewsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${state.message}")
            }
        }

        is NewsUiState.Success -> {
            val articles = state.articles
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Latest News",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize().testTag("news_list"),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = articles,
                        key = { _, article -> article.title }
                    ) { _, article ->
                        NewsItem(article = article, onClick = {
                            onNewsClick(article)
                        })
                    }
                }
            }
        }

        NewsUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No news available")
            }
        }
    }
}