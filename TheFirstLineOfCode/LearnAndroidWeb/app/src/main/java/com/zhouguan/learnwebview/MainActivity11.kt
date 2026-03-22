package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.bean.App
import com.zhouguan.learnwebview.bean.Person
import com.zhouguan.learnwebview.databinding.ActivityMain11Binding
import com.zhouguan.learnwebview.`interface`.AppService
import com.zhouguan.learnwebview.`interface`.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity11 : AppCompatActivity() {
    private val mBinding by lazy { ActivityMain11Binding.inflate(layoutInflater) }

    companion object {
        private const val TAG = "MainActivity11"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.getAppDataBtn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://apifoxmock.com/m1/4944163-4601795-default/kotlinstudyserver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val userService = retrofit.create(UserService::class.java)
            userService.getUserData().enqueue(object : Callback<List<Person>> {
                override fun onResponse(
                    call: Call<List<Person>?>,
                    response: Response<List<Person>?>
                ) {
                    val list = response.body()
                    if (list != null) {
                        for (app in list) {
                            Log.d(TAG, "name is ${app.name}")
                            Log.d(TAG, "version is ${app.address}")
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<Person>?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
        }
    }
}