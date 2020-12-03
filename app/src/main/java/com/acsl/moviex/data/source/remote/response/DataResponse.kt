package com.acsl.moviex.data.source.remote.response

import com.google.gson.annotations.SerializedName

class MovieResponse(

    @SerializedName("page")
    var page: Int,
    @SerializedName("total_pages")
    var totalPage: Int,
    @SerializedName("results")
    var results: List<ResultResponse>
)

class ResultResponse(
    @SerializedName("poster_path")
    var posterPath: String?,
    var id: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_name")
    var originalName: String?,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("first_air_date")
    var firstAirDate: String?
)