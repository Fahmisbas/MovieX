package com.acsl.moviex.data.source.remote.response

import com.acsl.moviex.data.source.remote.request.ApiRequest
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.API_KEY
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.BASE_URL
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.LANGUAGE_PREF
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.PAGE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RemoteDataSource {


     fun getMoviesRemotely(): Call<MovieResponse> = getApiService().getAllMovies(API_KEY, LANGUAGE_PREF, PAGE)

     fun getTvShowsRemotely(): Call<MovieResponse> = getApiService().getAllTvShows(API_KEY, LANGUAGE_PREF, PAGE)


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