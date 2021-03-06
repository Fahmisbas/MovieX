package com.acsl.moviex.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.paging.TvShowDataSource
import com.acsl.moviex.data.source.remote.response.ApiService

class TvShowDataSourceFactory(private val apiService: ApiService) :
    DataSource.Factory<Int, DataEntity>() {

    val tvShowLiveDataSource = MutableLiveData<TvShowDataSource>()

    override fun create(): DataSource<Int, DataEntity> {
        val tvShowDataSource = TvShowDataSource(apiService)
        tvShowLiveDataSource.postValue(tvShowDataSource)
        return tvShowDataSource
    }
}