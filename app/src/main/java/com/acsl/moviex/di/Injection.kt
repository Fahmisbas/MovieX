package com.acsl.moviex.di

import com.acsl.moviex.data.source.DataRepository
import com.acsl.moviex.data.source.remote.response.RemoteDataSource

object Injection {
    fun provideRepository(): DataRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return DataRepository.getInstance(remoteDataSource)
    }
}