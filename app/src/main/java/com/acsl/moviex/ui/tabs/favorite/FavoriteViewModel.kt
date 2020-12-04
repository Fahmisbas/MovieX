package com.acsl.moviex.ui.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository

class FavoriteViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun getAllFavoriteMovies(): LiveData<PagedList<DataEntity>> {
        val moviesData = repository.getAllFavoriteMovies()
        return LivePagedListBuilder(moviesData, 10).build()
    }

    fun getAllFavoriteTvShows(): LiveData<PagedList<DataEntity>> {
        val data = repository.getAllFavoriteTvShows()
        return LivePagedListBuilder(data, 10).build()
    }
}