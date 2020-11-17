package com.acsl.moviex.data.source.remote.response

import androidx.lifecycle.MutableLiveData
import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.data.source.remote.request.ApiRequest
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.API_KEY
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.BASE_URL
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.LANGUAGE_PREF
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.PAGE
import com.acsl.moviex.util.EspressoIdlingResource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    fun getAllMovies(): Call<MovieResponse> = getApiService().getAllMovies(API_KEY, LANGUAGE_PREF, PAGE)

    fun getAllTvShows(): Call<MovieResponse> = getApiService().getAllTvShows(API_KEY, LANGUAGE_PREF, PAGE)

    fun getMovies(isSuccessful : (Boolean) -> Unit) : List<MovieEntity> {
        EspressoIdlingResource.increment()
        val tvShowList = arrayListOf<MovieEntity>()
        getAllTvShows().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        for (result in movieResponse.results) {
                            tvShowList.add(
                                MovieEntity(
                                    result.posterPath,
                                    result.id,
                                    result.originalName,
                                    result.overview
                                )
                            )
                        }
                        isSuccessful.invoke(true)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isSuccessful.invoke(false)
                t.printStackTrace()
            }
        })
        return tvShowList
    }


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }

        fun getApiService(): ApiRequest {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiRequest::class.java)
        }
    }


}