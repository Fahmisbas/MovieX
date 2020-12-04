package com.acsl.moviex.ui.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.vo.NetworkState

class HomeViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun getAllTvShows(): LiveData<PagedList<DataEntity>> = repository.getAllTvShows()

    fun getTvShowNetworkState(): LiveData<NetworkState> = repository.getTvShowNetworkState()

    fun tvListIsEmpty(): Boolean {
        return getAllTvShows().value?.isEmpty() ?: true
    }

    fun getAllMovies(): LiveData<PagedList<DataEntity>> = repository.getAllMovies()


    fun getMovieNetworkState(): LiveData<NetworkState> = repository.getMovieNetworkState()


    fun movieListIsEmpty(): Boolean {
        return getAllMovies().value?.isEmpty() ?: true
    }

}