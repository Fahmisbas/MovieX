package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity


interface AppDataSource {

    fun getAllMovies(): LiveData<PagedList<DataEntity>>

    fun getAllTvShows(): LiveData<PagedList<DataEntity>>

    fun getAllFavoriteMovies(): DataSource.Factory<Int, DataEntity>

    fun getAllFavoriteTvShows(): DataSource.Factory<Int, DataEntity>

    suspend fun insertFavorite(data: DataEntity)

    suspend fun deleteFavorite(data: DataEntity)

}