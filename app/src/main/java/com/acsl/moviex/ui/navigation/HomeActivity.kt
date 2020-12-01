package com.acsl.moviex.ui.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.viewpager.widget.ViewPager
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.factory.ViewModelFactory
import com.acsl.moviex.ui.tabs.SectionPagerAdapter
import com.acsl.moviex.util.gone
import com.acsl.moviex.util.visible
import com.acsl.moviex.vo.NetworkState
import com.acsl.moviex.vo.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeDataChange()
    }

    override fun onResume() {
        super.onResume()
        observeDataChange()

    }

    private fun observeDataChange() {
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.moviePagedList.observe(this, { movies ->
            viewModel.tvShowPagedList.observe(this, { tvShows ->
                viewModel.getMovieNetworkState().observe(this, { netStateMovie ->
                    viewModel.getTvNetworkState().observe(this, { netStateTv ->
                        updateUi(netStateMovie, netStateTv)
                        if (movies.isNotEmpty() && tvShows.isNotEmpty()) {
                            setupWithViewPager(movies, tvShows, netStateMovie, netStateTv)
                        }

                    })
                })
            })
        })
    }

    private fun updateUi(netStateMovie: NetworkState, netStateTvShow: NetworkState) {
        if (netStateMovie.status == Status.RUNNING && netStateTvShow.status == Status.RUNNING) {
            if (viewModel.movieListIsEmpty()) {
                runningView()
            }
        } else if (netStateMovie.status == Status.SUCCESS && netStateTvShow.status == Status.SUCCESS) {
            successView()
        } else if (netStateMovie.status == Status.FAIL && netStateTvShow.status == Status.FAIL) {
            if (viewModel.movieListIsEmpty()) {
                failedView()
            }
        }
    }


    private fun setupWithViewPager(
        movies: PagedList<DataEntity>,
        tvShows: PagedList<DataEntity>,
        networkStateMovi: NetworkState,
        networkStateTv: NetworkState
    ) {
        nav.setOnNavigationItemSelectedListener(this)
        val sectionPagerAdapter =
            SectionPagerAdapter(
                this, supportFragmentManager, movies, tvShows, networkStateMovi, networkStateTv
            )
        view_pager.adapter = sectionPagerAdapter


        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> nav.menu.findItem(R.id.nav_movie).isChecked = true
                    1 -> nav.menu.findItem(R.id.nav_tv_shows).isChecked = true
                    2 -> nav.menu.findItem(R.id.nav_favorites).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_movie -> {
                view_pager.currentItem = 0
                true
            }
            R.id.nav_tv_shows -> {
                view_pager.currentItem = 1
                true
            }
            R.id.nav_favorites -> {
                view_pager.currentItem = 2
                true
            }
            else -> false
        }
    }

    private fun runningView() {
        load_viewpager.visible()
        img_error.gone()
        tv_error.gone()
    }

    private fun successView() {
        load_viewpager.gone()
        img_error.gone()
        tv_error.gone()
    }

    private fun failedView() {
        load_viewpager.gone()
        img_error.visible()
        tv_error.visible()
    }
}