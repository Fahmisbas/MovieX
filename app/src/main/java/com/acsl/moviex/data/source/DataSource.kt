package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import com.acsl.moviex.data.entities.DataEntity


interface DataSource {

    fun getAllMovies(): LiveData<List<DataEntity>>
    fun getAllTvShows(): LiveData<List<DataEntity>>
}