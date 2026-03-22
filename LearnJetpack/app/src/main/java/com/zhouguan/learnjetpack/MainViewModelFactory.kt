package com.zhouguan.learnjetpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhouguan.learnjetpack.ViewModel.MainViewModel

class MainViewModelFactory(private val counterReserved: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(counterReserved) as T
    }
}