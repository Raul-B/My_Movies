package com.example.mymovies2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovies2.databinding.ActivityMainBinding
import com.example.mymovies2.model.MovieDBClient
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesAdapter = MoviesAdapter(
            emptyList()
            /*listOf(
                Movie("Title 1", "https://loremflickr.com/320/240?lock=1"),
                Movie("Title 2", "https://loremflickr.com/320/240?lock=2"),
                Movie("Title 3", "https://loremflickr.com/320/240?lock=3"),
                Movie("Title 4", "https://loremflickr.com/320/240?lock=4"),
                Movie("Title 5", "https://loremflickr.com/320/240?lock=5"),
                Movie("Title 6", "https://loremflickr.com/320/240?lock=6")
            )*/
        ) {
            Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
        }

        binding.recycler.adapter = moviesAdapter

        thread {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDBClient.service.listPopularMovies(apiKey)
            val body = popularMovies.execute().body()

            runOnUiThread {
                if (body != null)
                    moviesAdapter.movies = body.results
                    moviesAdapter.notifyDataSetChanged()
                    //Log.d("MainActivity", "Movie count: ${body.results.size}")
            }

        }

    }
}