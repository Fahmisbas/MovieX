package com.acsl.moviex.ui.tabs

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.paging.PagedList
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.ui.tabs.favorite.FavoriteFragment
import com.acsl.moviex.vo.NetworkState

class SectionPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private var movies: PagedList<DataEntity>,
    private var tvShow: List<DataEntity>,
    private var networkState: NetworkState
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
        when (position) {
            0 -> return TabsFragment.newInstance(0, movies, networkState)
            1 -> return TabsFragment.newInstance(1, movies, networkState)
            2 -> return FavoriteFragment.newInstance(tvShow)

        }
        return TabsFragment.newInstance(0, movies, networkState)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

}