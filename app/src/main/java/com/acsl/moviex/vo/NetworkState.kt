package com.acsl.moviex.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NetworkState(val status: Status, val message: String?) : Parcelable {
    companion object {

        fun running(): NetworkState = NetworkState(Status.RUNNING, "Running!")

        fun success(): NetworkState = NetworkState(Status.SUCCESS, "Success!")

        fun error(): NetworkState = NetworkState(Status.FAIL, "Something went wrong")

        fun reachedBottomList(): NetworkState = NetworkState(Status.FAIL, "Reached the bottom")
    }
}
