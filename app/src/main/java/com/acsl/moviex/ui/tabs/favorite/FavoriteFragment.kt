package com.acsl.moviex.ui.tabs.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.acsl.moviex.R
import com.acsl.moviex.ui.tabs.favorite.movie.MovieFavoriteActivity
import com.acsl.moviex.ui.tabs.favorite.tvshow.TvShowFavoriteActivity
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_fav_movie.setOnClickListener {
            navigateToActivity(MovieFavoriteActivity::class.java)
        }

        btn_fav_tvshow.setOnClickListener {
            navigateToActivity(TvShowFavoriteActivity::class.java)
        }
    }

    private fun <T : AppCompatActivity> navigateToActivity(destination: Class<T>) {
        Intent(activity, destination).apply {
            startActivity(this)
        }
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}