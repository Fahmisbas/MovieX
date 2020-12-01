package com.acsl.moviex.ui.tabs.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.vo.NetworkState

class MovieViewModel(private val repository: AppDataRepository) : ViewModel() {

    val moviePagedList: LiveData<PagedList<DataEntity>> by lazy {
        repository.getAllMovies()
    }

    val movieNetworkState: LiveData<NetworkState> by lazy {
        repository.getMovieNetworkState()
    }

    fun movieListIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }
}