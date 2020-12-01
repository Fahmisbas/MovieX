package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.factory.MovieDataSourceFactory
import com.acsl.moviex.factory.TvShowDataSourceFactory
import com.acsl.moviex.ui.tabs.movie.MovieDataSource
import com.acsl.moviex.ui.tabs.tvshow.TvShowDataSource
import com.acsl.moviex.vo.NetworkState

open class AppDataRepository(private val apiService: ApiService) : DataSource {

    var moviesDataSourceFactory: MovieDataSourceFactory = MovieDataSourceFactory(apiService)
    var tvShowDataSourceFactory: TvShowDataSourceFactory = TvShowDataSourceFactory(apiService)

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(5)
        .setInitialLoadSizeHint(10)
        .build()


    override fun getAllMovies(): LiveData<PagedList<DataEntity>> {
        return LivePagedListBuilder(moviesDataSourceFactory, config).build()
    }

    fun getMovieNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }

    override fun getAllTvShows(): LiveData<PagedList<DataEntity>> {
        return LivePagedListBuilder(tvShowDataSourceFactory, config).build()
    }

    fun getTvNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<TvShowDataSource, NetworkState>(
            tvShowDataSourceFactory.tvShowLiveDataSource, TvShowDataSource::networkState
        )
    }

    companion object {
        @Volatile
        private var instance: AppDataRepository? = null
        fun getInstance(remoteData: ApiService): AppDataRepository =
            instance ?: synchronized(this) {
                instance ?: AppDataRepository(remoteData)
            }
    }

}