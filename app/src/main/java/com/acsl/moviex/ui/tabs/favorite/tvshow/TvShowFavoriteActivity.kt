package com.acsl.moviex.ui.tabs.favorite.tvshow

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
import com.acsl.moviex.ui.tabs.favorite.FavoriteViewModel
import com.acsl.moviex.util.gone
import com.acsl.moviex.util.visible
import kotlinx.android.synthetic.main.activity_tv_show_favorite.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TvShowFavoriteActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteViewModel
    private val listAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_favorite)

        setToolbar()

        intiViewModel()

        initRecyclerview()

        observeData()

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar_tv_show as Toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar_title.text = resources.getString(R.string.fav_tv_show_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initRecyclerview() {
        rv_favorite_tv_show.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
        onItemClickListener()
    }

    private fun onItemClickListener() {
        listAdapter.onItemClickCallback = object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataEntity) {
                Intent(this@TvShowFavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_DATA_DETAIL, data)
                    putExtra(DetailActivity.EXTRA_IS_FAVORITE, true)
                    startActivity(this)
                }
            }
        }
    }

    private fun observeData() {
        viewModel.getAllFavoriteTvShows().observe(this, { data ->
            if (data.isNotEmpty()) {
                listAdapter.submitList(data)
                listAdapter.notifyDataSetChanged()
            } else {
                tv_error_tv_show_favorite.visible()
                img_error_tv_show_favorite.visible()
                tv_error_tv_show_favorite.text = resources.getString(R.string.empty_fav_tv_show)
            }
            load_viewpager.gone()
        })
    }

    private fun intiViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }
}