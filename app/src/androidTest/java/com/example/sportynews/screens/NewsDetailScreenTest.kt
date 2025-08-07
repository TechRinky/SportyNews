package com.example.sportynews.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.sportynews.MainActivity
import com.example.sportynews.domain.models.NewsArticle
import org.junit.Rule
import org.junit.Test

class NewsDetailScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsArticleDetailsCorrectly() {
        val testArticle = NewsArticle(
            title = "Test News Title",
            content = "This is the full content of the test news article.",
            description = "Rinky",
            imageUrl = "https://example.com/image.jpg",

        )

        composeTestRule.setContent {
            NewsDetailScreen(article = testArticle, navController = rememberNavController())
        }

        composeTestRule
            .onNodeWithTag("articleTitle")
            .assertIsDisplayed()
    }
}