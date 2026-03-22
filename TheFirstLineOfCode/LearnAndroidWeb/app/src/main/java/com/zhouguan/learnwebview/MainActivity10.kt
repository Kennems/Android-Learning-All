package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain10Binding
import com.zhouguan.learnwebview.util.HttpUtil
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import kotlin.concurrent.thread

/**
 * Retrofit 使用
 */
class MainActivity10 : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity10"
    }

    private val mBinding by lazy { ActivityMain10Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.sendRequestBtn.setOnClickListener {
            sendRequestWithOkHttp()
        }

    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val address = "https://apifoxmock.com/m1/4944163-4601795-default/kotlinstudyserver/user"
                val response = HttpUtil.sendOkHttpRequest(address, object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d(TAG, "onFailure: $e")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d(TAG, "onResponse: $response")
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}