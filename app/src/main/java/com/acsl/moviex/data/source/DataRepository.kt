package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.RemoteDataSource
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class DataRepository(private val remoteDataSource: RemoteDataSource) : DataSource {


    override fun getAllMovies(): LiveData<List<DataEntity>> {
        EspressoIdlingResource.increment()
        val movies = MutableLiveData<List<DataEntity>>()
        remoteDataSource.getMoviesRemotely().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback ->
                        val moviesList = arrayListOf<DataEntity>()
                        for (result in callback.results) {
                            moviesList.add(
                                DataEntity(
                                    result.posterPath,
                                    result.id,
                                    result.originalTitle,
                                    result.overview
                                )
                            )
                        }
                        movies.postValue(moviesList)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return movies
    }

    override fun getAllTvShows(): LiveData<List<DataEntity>> {
        EspressoIdlingResource.increment()
        val tvShows = MutableLiveData<List<DataEntity>>()
        remoteDataSource.getTvShowsRemotely().enqueue(object : Callback<MovieResponse> {
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
        fun getInstance(remoteData: RemoteDataSource): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteData)
            }
    }


}