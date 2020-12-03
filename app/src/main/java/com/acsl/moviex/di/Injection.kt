package com.acsl.moviex.di

import android.content.Context
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.data.source.local.FavoriteDatabase
import com.acsl.moviex.data.source.remote.response.ApiService

object Injection {
    fun provideRepository(context: Context): AppDataRepository {
        val remoteDataSource = ApiService.getInstance()
        val localDataSource = FavoriteDatabase.getDatabase(context)
        return AppDataRepository.getInstance(remoteDataSource, localDataSource.favoriteDao)
    }
}