package com.magh.mod6practica1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magh.mod6practica1.data.MusicRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MusicRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(repository) as T

}