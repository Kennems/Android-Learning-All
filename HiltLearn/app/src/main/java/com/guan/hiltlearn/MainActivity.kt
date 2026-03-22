package com.guan.hiltlearn

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.guan.hiltlearn.databinding.ActivityMainBinding
import com.guan.hiltlearn.di.ApiService
import com.guan.hiltlearn.di.NetModule_ProvideOkHttpClientFactory
import com.guan.hiltlearn.di.User
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()

    @Inject
    lateinit var user: User
    @Inject
    lateinit var user2: User

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var apiService2: ApiService

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv.text = viewModel.getGreeting("kennem")

//        DaggerApplicationComponent.create().inject(this)
        Log.d("Kennem", "onCreate: user$user ${user.hashCode()}")
        Log.d("Kennem", "onCreate: user$user2 ${user2.hashCode()}")
        Log.d("Kennem", "onCreate: retrofit$retrofit ${retrofit.hashCode()}")
        Log.d("Kennem", "onCreate: apiService$apiService ${apiService.hashCode()}")
        Log.d("Kennem", "onCreate: apiService$apiService2 ${apiService2.hashCode()}")

        Log.d("Kennem", "onCreate: okHttpClient$okHttpClient ${okHttpClient.hashCode()}")

    }
}