package com.acsl.moviex.di

import com.acsl.moviex.data.source.DataRepository
import com.acsl.moviex.data.source.remote.response.ApiService

object Injection {
    fun provideRepository(): DataRepository {
        val remoteDataSource = ApiService.getInstance()
        return DataRepository.getInstance(remoteDataSource)
    }
}