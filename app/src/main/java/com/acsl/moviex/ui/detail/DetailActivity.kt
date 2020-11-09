package com.acsl.moviex.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acsl.moviex.R
import com.acsl.moviex.data.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Movie>(EXTRA_MOVIE_DETAIL)
        data?.let {
            tv_movie_name.text = it.movieName
            tv_overview.text = it.overview

            Glide.with(applicationContext)
                .load(getImage(data.imageName,applicationContext))
                .into(img_movie)
        }
    }

    private fun getImage(imageName: String?, context: Context): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }

    companion object {
        const val EXTRA_MOVIE_DETAIL = "movie_detail"
    }
}