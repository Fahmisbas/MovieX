package com.acsl.moviex.di

import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.data.source.remote.response.ApiService

object Injection {
    fun provideRepository(): AppDataRepository {
        val remoteDataSource = ApiService.getInstance()
        return AppDataRepository.getInstance(remoteDataSource)
    }
}