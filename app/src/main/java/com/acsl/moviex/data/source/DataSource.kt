package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import com.acsl.moviex.data.entities.MovieEntity


interface DataSource {

    fun getAllMovies() : LiveData<List<MovieEntity>>
    fun getAllTvShows() : LiveData<List<MovieEntity>>
}