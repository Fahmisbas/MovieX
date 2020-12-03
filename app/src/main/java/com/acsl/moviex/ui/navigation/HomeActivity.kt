package com.acsl.moviex.ui.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.acsl.moviex.R
import com.acsl.moviex.ui.adapter.SectionPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
    }

    private fun setupViewPager() {

        bottom_nav.setOnNavigationItemSelectedListener(this)
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
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
                    0 -> bottom_nav.menu.findItem(R.id.nav_movie).isChecked = true
                    1 -> bottom_nav.menu.findItem(R.id.nav_tv_shows).isChecked = true
                    else -> bottom_nav.menu.findItem(R.id.nav_favorites).isChecked = true
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
}