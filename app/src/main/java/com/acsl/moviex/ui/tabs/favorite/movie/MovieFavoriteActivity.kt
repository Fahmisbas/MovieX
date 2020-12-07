package com.acsl.moviex.ui.tabs.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.factory.ViewModelFactory
import com.acsl.moviex.ui.adapter.ListAdapter
import com.acsl.moviex.ui.detail.DetailActivity
import com.acsl.moviex.ui.detail.DetailActivity.Companion.EXTRA_DATA_DETAIL
import com.acsl.moviex.ui.detail.DetailActivity.Companion.EXTRA_IS_FAVORITE
import com.acsl.moviex.ui.tabs.favorite.FavoriteViewModel
import com.acsl.moviex.util.gone
import com.acsl.moviex.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_favorite.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MovieFavoriteActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteViewModel
    private var listAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_favorite)

        setToolbar()

        initViewModel()

        initRecyclerView()

        observeData()

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar_movie as Toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar_title.text = resources.getString(R.string.fav_movie_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun observeData() {
        viewModel.getAllFavoriteMovies().observe(this, { data ->
            if (data.isNotEmpty()) {
                listAdapter.submitList(data)
                listAdapter.notifyDataSetChanged()
            } else {
                tv_error_movie_favorite.visible()
                img_error_movie_favorite.visible()
                tv_error_movie_favorite.text = resources.getString(R.string.empty_fav_movie)
            }
            load_viewpager.gone()
        })
    }

    private fun initRecyclerView() {
        rv_movie_favorite.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
        onItemClickListener()
    }

    private fun onItemClickListener() {
        listAdapter.onItemClickCallback = object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataEntity) {
                Intent(this@MovieFavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(EXTRA_DATA_DETAIL, data)
                    putExtra(EXTRA_IS_FAVORITE, true)
                    startActivity(this)
                }
            }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }
}