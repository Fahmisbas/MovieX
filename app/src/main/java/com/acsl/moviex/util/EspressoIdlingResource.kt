package com.acsl.moviex.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"
    @JvmField val espressoTestIdResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdResource.increment()
    }

    fun decrement() {
        if (!espressoTestIdResource.isIdleNow) {
            espressoTestIdResource.decrement()
        }
    }

}