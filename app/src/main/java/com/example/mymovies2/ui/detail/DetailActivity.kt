package com.example.mymovies2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.Glide
import com.example.mymovies2.R
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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE) //putExtra(String, Parcelable)

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
            appendInfo(R.string.original_languges, movie.original_language)
            appendInfo(R.string.original_title, movie.title)
            appendInfo(R.string.release_date, movie.release_date)
            appendInfo(R.string.popularity, movie.popularity.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
        }
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value: String){
        bold {
            append(getString(stringRes))
            append(": ")
        }
        appendLine(value)
    }
}