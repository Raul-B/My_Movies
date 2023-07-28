package com.example.mymovies2.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDBClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(TheMovieDBServices::class.java)
}