package com.acsl.moviex.data.source.remote.response

import com.acsl.moviex.BuildConfig
import com.google.gson.annotations.SerializedName

class MovieResponse(

    @SerializedName("results")
    var results: List<ResultResponse>
) {
    companion object {
        const val API_KEY = BuildConfig.ApiKey
        const val BASE_IMAGE_URL = BuildConfig.BaseImageUrl
        const val BASE_URL = BuildConfig.BaseUrl
        const val LANGUAGE_PREF = "en-US"
        const val PAGE = "1"
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