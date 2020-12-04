package com.acsl.moviex.ui.navigation.dummy

import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.factory.MovieDataSourceFactory
import com.acsl.moviex.factory.TvShowDataSourceFactory

object DataDummy {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(5)
        .setInitialLoadSizeHint(10)
        .build()

    val remoteDataSource = ApiService.getInstance()

    private val moviesDataSourceFactory: MovieDataSourceFactory =
        MovieDataSourceFactory(remoteDataSource)
    private val tvShowDataSourceFactory: TvShowDataSourceFactory =
        TvShowDataSourceFactory(remoteDataSource)


    fun generateDummyMovies(): List<DataEntity> {
        val movies: ArrayList<DataEntity> = ArrayList()
        movies.add(
            DataEntity(
                "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
                "400160",
                "1234",
                "Bronx",
                "testest",
                "2020-10-30",
                1
            )
        )
        return movies
    }

    fun generateDummyTvShows(): List<DataEntity> {
        val tvShows: ArrayList<DataEntity> = ArrayList()
        tvShows.add(
            DataEntity(
                "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                "82856",
                "12344",
                "The Mandalorian",
                "Testtest",
                "2019-11-12",
                2
            )
        )
        return tvShows
    }

}

