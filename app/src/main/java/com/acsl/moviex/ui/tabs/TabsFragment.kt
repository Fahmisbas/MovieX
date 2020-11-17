package com.acsl.moviex.ui.tabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.ui.adapter.ListMovieAdapter
import com.acsl.moviex.ui.detail.DetailActivity
import com.acsl.moviex.ui.detail.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import kotlinx.android.synthetic.main.fragment_tabs.*

class TabsFragment(context: Context) : Fragment() {

    private var adapter = ListMovieAdapter(arrayListOf(),context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tabs.adapter = adapter

        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(SECTION_NUMBER, 0) as Int
        }
        setList(index)

    }

    private fun setList(index: Int) {
        if (index == 1) {
            arguments?.getParcelableArrayList<MovieEntity>(MOVIES)?.let {
                updateList(it)
            }
        } else {
            arguments?.getParcelableArrayList<MovieEntity>(TV_SHOWS)?.let {
                updateList(it)
            }
        }
    }

    private fun updateList(list: ArrayList<MovieEntity>) {
        adapter.updateList(list) { isNotEmpty ->
            if (isNotEmpty) {
                onItemClicked()
            }
        }
    }

    private fun onItemClicked() {
        adapter.onItemClickCallback = object : ListMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(movie: MovieEntity) {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(EXTRA_MOVIE_DETAIL, movie)
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        private const val SECTION_NUMBER = "section_number"
        private const val MOVIES = "user_following"
        private const val TV_SHOWS = "user_followers"

        fun newInstance(
            index: Int,
            movies: List<MovieEntity>,
            tvShows: List<MovieEntity>,
            context : Context
        ): TabsFragment {
            val fragment = TabsFragment(context)
            val bundle = Bundle()

            bundle.putParcelableArrayList(MOVIES, movies as ArrayList<MovieEntity>)
            bundle.putParcelableArrayList(TV_SHOWS, tvShows as ArrayList<MovieEntity>)
            bundle.putInt(SECTION_NUMBER, index)

            fragment.arguments = bundle
            return fragment
        }
    }
}