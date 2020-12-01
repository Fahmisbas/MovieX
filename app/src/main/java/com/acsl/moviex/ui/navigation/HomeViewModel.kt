package com.acsl.moviex.ui.navigation


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.vo.NetworkState

class HomeViewModel(private val repository: AppDataRepository) : ViewModel() {

    val movieNetworkState: LiveData<NetworkState> by lazy {
        repository.getMovieNetworkState()
    }

    val tvNetworkState: LiveData<NetworkState> by lazy {
        repository.getTvNetworkState()
    }

}