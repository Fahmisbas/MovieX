package com.acsl.moviex.ui.navigation

import androidx.lifecycle.ViewModel
import com.acsl.moviex.data.Movie
import com.acsl.moviex.data.repositories.MovieRepository

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    fun getMovieData() : ArrayList<Movie>? = repository.getMovieData()

    fun getTvShowData() : ArrayList<Movie>? = repository.getTvShowData()
}