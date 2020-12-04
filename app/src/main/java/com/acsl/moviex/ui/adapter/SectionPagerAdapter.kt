package com.acsl.moviex.ui.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.acsl.moviex.R
import com.acsl.moviex.ui.tabs.favorite.FavoriteFragment
import com.acsl.moviex.ui.tabs.movie.MovieFragment
import com.acsl.moviex.ui.tabs.tvshow.TvShowFragment

class SectionPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.tab_title_movie,
        R.string.tab_title_tvshow,
        R.string.tab_title_favorite
    )

    override fun getCount() = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment.newInstance()
            1 -> TvShowFragment.newInstance()
            else -> FavoriteFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

}