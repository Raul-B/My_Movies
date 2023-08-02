package com.example.mymovies2.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class MainActivity : AppCompatActivity() {
    companion object{private const val DEFAULT_REGION = "US"}

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
        doRequestPopularMovies(isGranted)
    }

    private val moviesAdapter = MoviesAdapter(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.recycler.adapter = moviesAdapter
    }

    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val region = getRegion(isLocationGranted)
            val popularMovies = MovieDBClient.service.listPopularMovies(apiKey, region)

            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
            Log.d("MainActivity", "Movie count: ${popularMovies.results.size}")
        }
    }
    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String = suspendCancellableCoroutine {continuation->
        if (isLocationGranted){
            fusedLocationClient.lastLocation.addOnCompleteListener{
                continuation.resume(getRegionFromLocation(it.result))
            }
        }else{
            continuation.resume(DEFAULT_REGION)
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        if (location == null){
            return DEFAULT_REGION
        }

        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
            return result?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}