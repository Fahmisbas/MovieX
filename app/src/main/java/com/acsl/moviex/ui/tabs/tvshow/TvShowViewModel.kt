package com.acsl.moviex.ui.tabs.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.vo.NetworkState

class TvShowViewModel(private val repository: AppDataRepository) : ViewModel() {

    val tvShowPagedList: LiveData<PagedList<DataEntity>> by lazy {
        repository.getAllTvShows()
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getTvNetworkState()
    }

    fun tvListIsEmpty(): Boolean {
        return tvShowPagedList.value?.isEmpty() ?: true
    }

}