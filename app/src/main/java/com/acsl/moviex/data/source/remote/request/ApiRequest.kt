package com.acsl.moviex.data.source.remote.request


import com.acsl.moviex.data.source.remote.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("3/movie/popular")
    fun getAllMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/tv/popular")
    fun getAllTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>
}