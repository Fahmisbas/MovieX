package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity


interface DataSource {

    fun getAllMovies(): LiveData<PagedList<DataEntity>>

    fun getAllTvShows(): LiveData<List<DataEntity>>
}