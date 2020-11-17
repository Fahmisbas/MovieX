package com.acsl.moviex.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"
    val espressoTestIdResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdResource.increment()
    }

    fun decrement() {
        espressoTestIdResource.decrement()
    }

}