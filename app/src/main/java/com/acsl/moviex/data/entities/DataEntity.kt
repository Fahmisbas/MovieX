package com.acsl.moviex.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class DataEntity(
    var posterPath: String?,
    var id: String?,
    var originalTitle: String?,
    var overview: String?
) : Parcelable