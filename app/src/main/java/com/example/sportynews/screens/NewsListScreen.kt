package com.example.sportynews.screens


import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sportynews.components.NewsItem
import com.example.sportynews.presentation.NewsViewModel
import com.example.sportynews.sealedclasses.NewsUiState
import com.example.sportynews.utility.toJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
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
                Text("Error: ${(state as NewsUiState.Error).message}")
            }
        }

        is NewsUiState.Success -> {
            val articles = (state as NewsUiState.Success).articles
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
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(12.dp) // ✅ Better than Spacer
                ) {
                    itemsIndexed(
                        items = articles,
                        key = { _, article -> article.title } // ✅ Helps reuse items
                    ) { _, article ->
                        NewsItem(article = article, onClick = {
                            // Handle navigation if needed
                            val articleJson = Uri.encode(article.toJson())
                            navController.navigate("news_detail/$articleJson")
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