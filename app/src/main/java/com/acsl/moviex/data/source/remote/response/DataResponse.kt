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
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_name")
    var originalName : String?,
    var overview: String?
)