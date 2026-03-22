package com.guan.hiltlearn

import jakarta.inject.Inject

interface GreetingService {
    fun greet(name: String): String
}

class GreetingServiceImpl @Inject constructor(
) : GreetingService {
    override fun greet(name: String): String {
        return "Hello, $name!"
    }
}
