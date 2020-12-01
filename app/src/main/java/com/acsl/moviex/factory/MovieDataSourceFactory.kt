package com.acsl.moviex.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.ui.tabs.movie.MovieDataSource

class MovieDataSourceFactory(private val apiService: ApiService) :
    DataSource.Factory<Int, DataEntity>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, DataEntity> {
        val movieDataSource = MovieDataSource(apiService)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}