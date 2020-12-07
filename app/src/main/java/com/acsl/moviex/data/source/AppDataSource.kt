package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity


interface AppDataSource {

    fun getAllMovies(): LiveData<PagedList<DataEntity>>

    fun getAllTvShows(): LiveData<PagedList<DataEntity>>

    fun getAllFavoriteMovies(): LiveData<PagedList<DataEntity>>

    fun getAllFavoriteTvShows(): LiveData<PagedList<DataEntity>>

    suspend fun insertFavorite(data: DataEntity)

    suspend fun deleteFavorite(data: DataEntity)

}