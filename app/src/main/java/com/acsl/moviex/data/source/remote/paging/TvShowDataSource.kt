package com.acsl.moviex.data.source.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.ApiService
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.util.EspressoIdlingResource
import com.acsl.moviex.util.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDataSource(private val apiService: ApiService) :
    PageKeyedDataSource<Int, DataEntity>() {

    private var page = 1
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataEntity>
    ) {
        EspressoIdlingResource.increment()

        networkState.postValue(NetworkState.running())

        apiService.getAllTvShows(page).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { networkCallback ->
                        val tvShowList = arrayListOf<DataEntity>()
                        for (result in networkCallback.results) {
                            tvShowList.add(
                                DataEntity(
                                    result.posterPath,
                                    result.backdropPath,
                                    result.id ?: "",
                                    result.originalName,
                                    result.overview,
                                    result.firstAirDate,
                                    2
                                )
                            )
                        }
                        callback.onResult(tvShowList, null, page + 1)
                        networkState.postValue(NetworkState.success())
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error())
                t.printStackTrace()
            }
        })
        EspressoIdlingResource.decrement()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataEntity>) {
        EspressoIdlingResource.increment()

        networkState.postValue(NetworkState.running())

        apiService.getAllTvShows(params.key).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { networkCallback ->
                        val tvShowList = arrayListOf<DataEntity>()
                        for (result in networkCallback.results) {
                            tvShowList.add(
                                DataEntity(
                                    result.posterPath,
                                    result.backdropPath,
                                    result.id ?: "",
                                    result.originalName,
                                    result.overview,
                                    result.firstAirDate,
                                    2
                                )
                            )
                        }
                        if (networkCallback.totalPage >= params.key) {
                            callback.onResult(tvShowList, params.key + 1)
                            networkState.postValue(NetworkState.success())
                        } else {
                            networkState.postValue(NetworkState.reachedBottomList())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error())
                t.printStackTrace()
            }
        })
        EspressoIdlingResource.decrement()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataEntity>) {}
}