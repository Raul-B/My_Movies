package com.example.mymovies2.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mymovies2.R
import com.example.mymovies2.databinding.ActivityMainBinding
import com.example.mymovies2.model.Movie
import com.example.mymovies2.model.MovieDBClient
import com.example.mymovies2.ui.detail.DetailActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted -> val message = if(isGranted) "Permission Granted" else "Permission Rejected"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

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
            //Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
            navigateTo(it)
        }

        binding.recycler.adapter = moviesAdapter

        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDBClient.service.listPopularMovies(apiKey)

            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
            Log.d("MainActivity", "Movie count: ${popularMovies.results.size}")
        }

    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}