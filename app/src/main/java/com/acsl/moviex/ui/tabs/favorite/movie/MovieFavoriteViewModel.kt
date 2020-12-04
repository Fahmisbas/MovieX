package com.acsl.moviex.ui.tabs.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository

class MovieFavoriteViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun getAllFavoriteMovies(): LiveData<PagedList<DataEntity>> {
        val moviesData = repository.getAllFavoriteMovies()
        return LivePagedListBuilder(moviesData, 10).build()
    }
}