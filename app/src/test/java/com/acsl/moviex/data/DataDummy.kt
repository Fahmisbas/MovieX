package com.acsl.moviex.data

import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.data.source.remote.response.ResultResponse
import retrofit2.Call

object DataDummy {

    fun generateDummyMovies(): List<MovieEntity> {
        val movies: ArrayList<MovieEntity> = ArrayList()
        movies.add(
            MovieEntity(
                "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
                "400160",
                "Bronx",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
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

    fun generateRemoteDummyMovies(): Call<MovieResponse> {
        val movies: ArrayList<MovieResponse> = ArrayList()
        movies.add(
            MovieResponse(
                listOf(ResultResponse(
                    "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
                    "400160",
                    "Bronx",
                    null,
                    "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.\""
                ))
            )
        )
        return movies
    }
}