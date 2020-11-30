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

    fun getAllMovies() = repository.getAllMovies()

    fun getAllTvShows() = repository.getAllTvShows()

    fun getNetworkState() = repository.getNetworkState()

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

}