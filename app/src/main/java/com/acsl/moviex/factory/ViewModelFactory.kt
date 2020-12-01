package com.acsl.moviex.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.data.source.AppDataRepository
import com.acsl.moviex.di.Injection
import com.acsl.moviex.ui.navigation.HomeViewModel
import com.acsl.moviex.ui.tabs.favorite.FavoriteViewModel
import com.acsl.moviex.ui.tabs.movie.MovieViewModel
import com.acsl.moviex.ui.tabs.tvshow.TvShowViewModel

class ViewModelFactory(private val repository: AppDataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }

}