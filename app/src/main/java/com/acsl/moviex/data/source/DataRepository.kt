package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.paging.MovieDataSource
import com.acsl.moviex.data.source.remote.paging.TvShowDataSource
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.factory.MovieDataSourceFactory
import com.acsl.moviex.factory.TvShowDataSourceFactory
import com.acsl.moviex.vo.NetworkState

open class DataRepository(private val apiService: ApiService) : DataSource {

    var moviesDataSourceFactory: MovieDataSourceFactory = MovieDataSourceFactory(apiService)
    var tvShowDataSourceFactory: TvShowDataSourceFactory = TvShowDataSourceFactory(apiService)

    override fun getAllMovies(): LiveData<PagedList<DataEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(moviesDataSourceFactory, config).build()
    }

    fun getMovieNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }


    override fun getAllTvShows(): LiveData<PagedList<DataEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(tvShowDataSourceFactory, config).build()
    }

    fun getTvNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<TvShowDataSource, NetworkState>(
            tvShowDataSourceFactory.tvShowLiveDataSource, TvShowDataSource::networkState
        )
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(remoteData: ApiService): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteData)
            }
    }

}