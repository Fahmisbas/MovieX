package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.paging.MovieDataSource
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.factory.MovieDataSourceFactory
import com.acsl.moviex.util.EspressoIdlingResource
import com.acsl.moviex.vo.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class DataRepository(private val apiService: ApiService) : DataSource {

    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    override fun getAllMovies(): LiveData<PagedList<DataEntity>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(moviesDataSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }

    override fun getAllTvShows(): LiveData<List<DataEntity>> {
        EspressoIdlingResource.increment()
        val tvShows = MutableLiveData<List<DataEntity>>()
        apiService.getAllTvShows(1).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback ->
                        val tvShowList = arrayListOf<DataEntity>()
                        for (result in callback.results) {
                            tvShowList.add(
                                DataEntity(
                                    result.posterPath,
                                    result.id,
                                    result.originalName,
                                    false,
                                    result.overview
                                )
                            )
                        }
                        tvShows.postValue(tvShowList)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return tvShows
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