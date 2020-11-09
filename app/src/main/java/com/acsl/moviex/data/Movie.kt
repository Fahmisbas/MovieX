package com.acsl.moviex.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Movie(
    var imageName : String?,
    var movieName : String?,
    var overview : String?
) : Parcelable