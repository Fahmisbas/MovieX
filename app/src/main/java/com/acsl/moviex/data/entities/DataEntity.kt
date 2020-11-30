package com.acsl.moviex.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataEntity(
    val posterPath: String?,
    val id: String?,
    val originalTitle: String?,
    var isFavorite: Boolean? = false,
    val overview: String?
) : Parcelable