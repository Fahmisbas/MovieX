package com.acsl.moviex.ui.tabs.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository

class TvShowFavoriteViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun getAllFavoriteTvShows(): LiveData<PagedList<DataEntity>> {
        val data = repository.getAllFavoriteTvShows()
        return LivePagedListBuilder(data, 10).build()
    }
}