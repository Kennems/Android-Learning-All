package com.guan.hiltlearn

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val greetingService: GreetingService
) : ViewModel() {
    fun getGreeting(name: String): String {
        return greetingService.greet(name)
    }
}