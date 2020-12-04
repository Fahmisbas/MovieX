package com.acsl.moviex.data.source.remote.response

import com.acsl.moviex.BuildConfig.ApiKey
import com.acsl.moviex.BuildConfig.BaseUrl
import com.acsl.moviex.data.source.remote.request.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ApiService {

    private val languagePref = "en-US"

    fun getAllMovies(page: Int): Call<MovieResponse> =
        getApiService().getAllMovies(ApiKey, languagePref, page)

    fun getAllTvShows(page: Int): Call<MovieResponse> =
        getApiService().getAllTvShows(ApiKey, languagePref, page)


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
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiRequest::class.java)
        }
    }
}