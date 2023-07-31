package com.example.mymovies2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.Glide
import com.example.mymovies2.databinding.ActivityDetailBinding
import com.example.mymovies2.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MOVIE = "DetailActivity.movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)

        if (movie!=null){
            title = movie.title
            binding.summary.text = movie.overview
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.backDrop)
            binDetailInfo(binding.detailInfo, movie)

        }

    }

    private fun binDetailInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {

            bold { append("Original languges:")}
            appendLine(movie.original_language)

            bold { append("Original title:")}
            appendLine(movie.title)

            bold { append("Realese date:")}
            appendLine(movie.release_date)

            bold { append("Popularity:")}
            appendLine(movie.popularity.toString())

            bold { append("Vote Average:")}
            appendLine(movie.vote_average.toString())
        }
    }
}