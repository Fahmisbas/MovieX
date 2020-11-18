package com.acsl.moviex.ui.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.ui.tabs.SectionPagerAdapter
import com.acsl.moviex.util.gone
import com.acsl.moviex.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observerDataChange()
    }


    private fun observerDataChange() {
        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.getAllMovies().observe(this, { movies ->
            viewModel.getAllTvShows().observe(this, { tvShows ->
                if (movies.isNotEmpty() && tvShows.isNotEmpty()) {
                    load_viewpager.gone()
                    setupWithViewPager(movies, tvShows)
                }
            })
        })
    }

    private fun setupWithViewPager(data: List<DataEntity>, tvShows: List<DataEntity>) {
        nav.setOnNavigationItemSelectedListener(this)
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, data, tvShows)
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
            else -> false
        }
    }


}