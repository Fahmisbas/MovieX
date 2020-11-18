package com.acsl.moviex.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.data.source.remote.response.MovieResponse
import com.acsl.moviex.data.source.remote.response.RemoteDataSource
import com.acsl.moviex.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class DataRepository(private val remoteDataSource: RemoteDataSource) : DataSource {

    private var _error = MutableLiveData<Boolean>()
    var error : LiveData<Boolean> = _error

    override fun getAllMovies() : LiveData<List<MovieEntity>>{
        EspressoIdlingResource.increment()
        val movies = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getMoviesRemotely().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback ->
                        val moviesList = arrayListOf<MovieEntity>()
                        for (result in callback.results) {
                            moviesList.add(
                                MovieEntity(
                                    result.posterPath,
                                    result.id,
                                    result.originalTitle,
                                    result.overview
                                )
                            )
                        }
                        movies.postValue(moviesList)
                        _error.postValue(false)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _error.postValue(true)
                t.printStackTrace()
            }
        })
        return movies
    }

    override fun getAllTvShows(): LiveData<List<MovieEntity>> {
        EspressoIdlingResource.increment()
        val tvShows = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getTvShowsRemotely().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback ->
                        val tvShowList = arrayListOf<MovieEntity>()
                        for (result in callback.results) {
                            tvShowList.add(
                                MovieEntity(
                                    result.posterPath,
                                    result.id,
                                    result.originalName,
                                    result.overview
                                )
                            )
                        }
                        tvShows.postValue(tvShowList)
                        _error.postValue(false)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _error.postValue(true)
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