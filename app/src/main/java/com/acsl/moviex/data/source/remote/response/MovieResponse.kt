package com.acsl.moviex.data.source.remote.response

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("results")
    var results : List<ResultResponse>
) {
    companion object {
        const val API_KEY = "API_KEY"
        const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/original"
        const val LANGUAGE_PREF = "en-US"
        const val PAGE = "1"
        const val BASE_URL = "https://api.themoviedb.org/"
    }
}

class ResultResponse(
    @SerializedName("poster_path")
    var posterPath: String?,
    var id: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_name")
    var originalName : String?,
    var overview: String?
)