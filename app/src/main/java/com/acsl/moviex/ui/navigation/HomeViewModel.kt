package com.acsl.moviex.ui.navigation

import androidx.lifecycle.ViewModel
import com.acsl.moviex.data.source.DataRepository

class HomeViewModel(private val repository: DataRepository) : ViewModel() {

    fun getAllMovies()  = repository.getAllMovies()
    fun getAllTvShows() = repository.getAllTvShows()

}