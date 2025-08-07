package com.example.sportynews.data.mapper

import com.example.sportynews.data.dtos.ArticleDto
import com.example.sportynews.domain.models.NewsArticle

fun ArticleDto.toDomain(): NewsArticle = NewsArticle(
    title = title,
    imageUrl = urlToImage,
    description = description,
    content= content
)