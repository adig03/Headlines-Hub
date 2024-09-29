package com.example.newsapp.util

data class newsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)