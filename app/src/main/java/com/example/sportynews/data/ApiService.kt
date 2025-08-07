package com.example.sportynews.data

import com.example.sportynews.data.dtos.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getTopHeadlines(
        @Query ("q") query: String = "tesla",
        @Query("apiKey") apiKey: String
    ): NewsResponseDto
}