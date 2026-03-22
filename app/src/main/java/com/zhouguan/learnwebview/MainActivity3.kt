package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain3Binding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

/**
 * OkHttp 使用
 */
class MainActivity3 : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity3"
    }

    private val mBinding by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
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

        val client = OkHttpClient()

        // GET
        val request1 = Request
            .Builder()
            .url("https://apifoxmock.com/m1/4944163-4601795-default/pet/1")
            .build()

        thread{
            try {
                val response1 = client
                    .newCall(request1)
                    .execute()
                val responseData1 = response1.body?.string()
                Log.d(TAG, "onCreate: $responseData1")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        // POST
        // POST 需要添加一个requestBody
        val requestBody = FormBody.Builder()
            .add("username", "admin")
            .add("password", "123456")
            .build()

        val request2 = Request
            .Builder()
            .url("https://apifoxmock.com/m2/4944163-4601795-default/254012024")
            .post(requestBody)
            .build()

        thread{
            try {
                val response2 = client
                    .newCall(request2)
                    .execute()
                val responseData2 = response2.body?.string()
                Log.d(TAG, "onCreate: $responseData2")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}