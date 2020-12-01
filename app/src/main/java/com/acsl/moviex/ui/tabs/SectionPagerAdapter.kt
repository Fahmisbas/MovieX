package com.acsl.moviex.ui.tabs

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.paging.PagedList
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.vo.NetworkState

class SectionPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private var movies: PagedList<DataEntity>,
    private var tvShow: PagedList<DataEntity>,
    private var networkState: NetworkState,
    private var tvNetworkState: NetworkState
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
            0 -> TabsFragment.newInstance(movies, networkState)
            1 -> TabsFragment.newInstance(tvShow, networkState)
            else -> TabsFragment.newInstance(tvShow, networkState)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

}