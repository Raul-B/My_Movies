package com.example.mymovies2.model

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBServices {
    @GET("movie/popular")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String): MovieDBResult
}