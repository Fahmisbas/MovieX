package com.acsl.moviex.ui.navigation


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.DataRepository

class HomeViewModel(private val repository: DataRepository) : ViewModel() {

    val moviePagedList: LiveData<PagedList<DataEntity>> by lazy {
        repository.getAllMovies()
    }

    val tvShowPagedList: LiveData<PagedList<DataEntity>> by lazy {
        repository.getAllTvShows()
    }

    fun getMovieNetworkState() = repository.getMovieNetworkState()

    fun getTvNetworkState() = repository.getTvNetworkState()

    fun movieListIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    fun tvListIsEmpty(): Boolean {
        return tvShowPagedList.value?.isEmpty() ?: true
    }

}