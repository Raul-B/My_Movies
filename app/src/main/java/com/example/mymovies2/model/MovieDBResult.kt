package com.example.mymovies2.model

data class MovieDBResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)