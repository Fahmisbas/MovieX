package com.acsl.moviex.util

import com.acsl.moviex.data.entities.MovieEntity

object DataDummy {

    fun generateDummyMovies(): List<MovieEntity> {
        val movies: ArrayList<MovieEntity> = ArrayList()
        movies.add(
            MovieEntity(
                "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
                "400160",
                "Bronx",
                "Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands."
            )
        )
        return movies
    }

    fun generateDummyTvShows(): List<MovieEntity> {
        val tvShows: ArrayList<MovieEntity> = ArrayList()
        tvShows.add(
            MovieEntity(
                "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                "82856",
                "The Mandalorian",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter."
            )
        )
        return tvShows
    }
}