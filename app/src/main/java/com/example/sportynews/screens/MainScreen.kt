package com.example.sportynews.screens

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportynews.domain.models.NewsArticle
import com.example.sportynews.utility.fromJson

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsListScreen(navController = navController)
        }
        composable(
            route = "news_detail/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("articleJson")
            val article = json?.let {
                Uri.decode(it).fromJson<NewsArticle>()
            }
            article?.let {
                NewsDetailScreen(article = it, navController = navController)
            }
        }
    }
}



