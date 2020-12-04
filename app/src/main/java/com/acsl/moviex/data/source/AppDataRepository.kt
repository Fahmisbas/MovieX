package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.local.FavoriteDao
import com.acsl.moviex.data.source.remote.paging.MovieDataSource
import com.acsl.moviex.data.source.remote.paging.TvShowDataSource
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.factory.MovieDataSourceFactory
import com.acsl.moviex.factory.TvShowDataSourceFactory
import com.acsl.moviex.util.NetworkState

open class AppDataRepository(
    remoteDataSource: ApiService,
    private val localDataSource: FavoriteDao
) : AppDataSource {

    private val moviesDataSourceFactory: MovieDataSourceFactory =
        MovieDataSourceFactory(remoteDataSource)
    private val tvShowDataSourceFactory: TvShowDataSourceFactory =
        TvShowDataSourceFactory(remoteDataSource)

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(5)
        .setInitialLoadSizeHint(10)
        .build()

    override fun getAllMovies(): LiveData<PagedList<DataEntity>> {
        return LivePagedListBuilder(moviesDataSourceFactory, config).build()
    }

    override fun getAllTvShows(): LiveData<PagedList<DataEntity>> {
        return LivePagedListBuilder(tvShowDataSourceFactory, config).build()
    }

    override fun getAllFavoriteMovies(): DataSource.Factory<Int, DataEntity> {
        return localDataSource.getAllFavoriteMovies()
    }

    override fun getAllFavoriteTvShows(): DataSource.Factory<Int, DataEntity> {
        return localDataSource.getAllFavoriteTvShows()
    }

    override suspend fun insertFavorite(data: DataEntity) {
        localDataSource.insert(data)
    }

    override suspend fun deleteFavorite(data: DataEntity) {
        localDataSource.delete(data)
    }

    fun getTvShowNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            tvShowDataSourceFactory.tvShowLiveDataSource, TvShowDataSource::networkState
        )
    }

    fun getMovieNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }

    companion object {
        @Volatile
        private var instance: AppDataRepository? = null
        fun getInstance(remoteData: ApiService, localData: FavoriteDao): AppDataRepository =
            instance ?: synchronized(this) {
                instance ?: AppDataRepository(remoteData, localData)
            }
    }
}