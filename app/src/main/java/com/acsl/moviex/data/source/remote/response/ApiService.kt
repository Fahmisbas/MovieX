package com.acsl.moviex.data.source.remote.response

import com.acsl.moviex.data.source.remote.request.ApiRequest
import com.acsl.moviex.data.source.remote.request.ApiRequest.Companion.API_KEY
import com.acsl.moviex.data.source.remote.request.ApiRequest.Companion.BASE_URL
import com.acsl.moviex.data.source.remote.request.ApiRequest.Companion.LANGUAGE_PREF
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ApiService {


    fun getAllMovies(page: Int): Call<MovieResponse> =
        getApiService().getAllMovies(API_KEY, LANGUAGE_PREF, page)

    fun getAllTvShows(page: Int): Call<MovieResponse> =
        getApiService().getAllTvShows(API_KEY, LANGUAGE_PREF, page)


    companion object {
        @Volatile
        private var instance: ApiService? = null

        fun getInstance(): ApiService =
            instance ?: synchronized(this) {
                instance ?: ApiService()
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