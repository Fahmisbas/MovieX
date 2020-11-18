package com.acsl.moviex.ui.tabs

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity

class SectionPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private var data: List<DataEntity>,
    private var tvShow: List<DataEntity>
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.tab_title_movie,
        R.string.tab_title_tvshow
    )

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            1 -> return TabsFragment.newInstance(0, data, tvShow, context)
            2 -> return TabsFragment.newInstance(1, data, tvShow, context)
        }
        return TabsFragment.newInstance(1, data, tvShow, context)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }
}