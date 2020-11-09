package com.acsl.moviex.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.R
import com.acsl.moviex.ui.tabs.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observerData()
    }

    private fun observerData() {
        val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        model.getMovieData()?.let {movies ->
            model.getTvShowData()?.let{tvShows ->
                val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, movies, tvShows)
                load_viewpager.visibility = View.GONE
                view_pager.adapter = sectionsPagerAdapter
                tabs.setupWithViewPager(view_pager)
            }
        }
    }

}