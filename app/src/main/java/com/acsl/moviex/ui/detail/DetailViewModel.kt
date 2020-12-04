package com.acsl.moviex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.AppDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AppDataRepository) : ViewModel() {

    fun insertFavorite(data: DataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(data)
        }
    }

    fun deleteFavorite(data: DataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}