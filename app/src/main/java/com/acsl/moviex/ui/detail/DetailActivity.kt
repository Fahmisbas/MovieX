package com.acsl.moviex.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<DataEntity>(EXTRA_MOVIE_DETAIL)
        data?.let {
            tv_movie_name.text = it.originalTitle
            tv_overview.text = it.overview

            Glide.with(this)
                .load(MovieResponse.BASE_IMAGE_URL +data.posterPath)
                .into(img_movie)
        }
    }

    companion object {
        const val EXTRA_MOVIE_DETAIL = "movie_detail"
    }
}