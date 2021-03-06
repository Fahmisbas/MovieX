package com.acsl.moviex.ui.navigation.dummy

import com.acsl.moviex.data.entities.DataEntity

object DataDummy {
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

