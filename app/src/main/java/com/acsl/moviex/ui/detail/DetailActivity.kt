package com.acsl.moviex.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.request.ApiRequest.Companion.BASE_IMAGE_URL
import com.acsl.moviex.factory.ViewModelFactory
import com.acsl.moviex.util.makeToast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class DetailActivity : AppCompatActivity() {

    private var statusFavorite = false
    private var isFavorite = false
    private var data: DataEntity? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        getData()
        initViewModel()
        setToolbar()

    }

    private fun getData() {
        data = intent.getParcelableExtra(EXTRA_DATA_DETAIL)
        data?.let { updateUi(it) }
        isFavorite = intent.getBooleanExtra(EXTRA_IS_FAVORITE, false)
        if (isFavorite) {
            showDeleteFunctionality()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun updateUi(movies: DataEntity) {
        movies.let {
            tv_movie_name.text = it.originalTitle
            tv_overview.text = it.overview
            tv_release_date.text = it.releaseDate
            toolbar_title.text = it.originalTitle

            Glide.with(this)
                .load(BASE_IMAGE_URL + movies.backdropPath)
                .into(img_movie)
        }
    }

    override fun onResume() {
        super.onResume()
        setUserFavorite()
    }

    private fun setUserFavorite() {
        btn_favorite.setOnClickListener {
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        when (statusFavorite) {
            true -> {
                if (isFavorite) {
                    showDeleteFunctionality()
                } else {
                    addTask()
                }
            }
            false -> {
                deleteTask()
            }
        }
    }

    private fun showDeleteFunctionality() {
        tv_favorite.text = resources.getString(R.string.remove_favorite)
        btn_favorite.setImageResource(R.drawable.ic_min)
        if (btn_favorite.isPressed) {
            deleteTask()
            this@DetailActivity.statusFavorite = false
        }
    }

    private fun deleteTask() {
        btn_favorite.setImageResource(R.drawable.ic_add)
        tv_favorite.text = resources.getString(R.string.add_favorite)
        data?.let {
            viewModel.deleteFavorite(it)
            isFavorite = false
            this.makeToast(resources.getString(R.string.successfully_removed))
        }

    }

    private fun addTask() {
        tv_favorite.text = resources.getString(R.string.remove_favorite)
        btn_favorite.setImageResource(R.drawable.ic_min)
        data?.let {
            viewModel.insertFavorite(it)
            this.makeToast(resources.getString(R.string.successfully_added))
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar_detail as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DATA_DETAIL = "movie_detail"
        const val EXTRA_IS_FAVORITE = "is_favorite"
    }
}