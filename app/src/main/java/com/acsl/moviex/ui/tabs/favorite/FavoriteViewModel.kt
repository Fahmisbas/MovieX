package com.acsl.moviex.ui.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository

open class FavoriteViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun getAllFavoriteMovies(): LiveData<PagedList<DataEntity>> = repository.getAllFavoriteMovies()

    fun getAllFavoriteTvShows(): LiveData<PagedList<DataEntity>> =
        repository.getAllFavoriteTvShows()

}