package com.example.sportynews.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sportynews.domain.models.NewsArticle
import com.example.sportynews.sealedclasses.NewsUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalMaterial3Api::class)
@RunWith(AndroidJUnit4::class)
class NewsListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsNews_whenUiStateIsSuccess() {
        val articles = listOf(
            NewsArticle("1", "Title 1", "Desc", "imageUrl")
        )

        composeTestRule.setContent {
            NewsListContent(
                state = NewsUiState.Success(articles),
                onNewsClick = {},
                onRetry = {}
            )
        }

        composeTestRule
            .onNodeWithTag("news_list")
            .assertExists() // assert it's present
            .assertIsDisplayed() // assert it's visible
    }
}