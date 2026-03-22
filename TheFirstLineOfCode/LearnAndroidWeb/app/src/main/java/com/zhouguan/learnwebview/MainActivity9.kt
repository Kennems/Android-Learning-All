package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain9Binding
import com.zhouguan.learnwebview.`interface`.HttpCallbackListener
import com.zhouguan.learnwebview.util.HttpUtil
import kotlin.concurrent.thread

class MainActivity9 : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity9"
    }

    private val mBinding by lazy { ActivityMain9Binding.inflate(layoutInflater) }

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
                val response = HttpUtil.sendHttpRequest(
                    address,
                    object : HttpCallbackListener {
                        override fun onFinish(response: String) {
                            Log.d(TAG, "onFinish: $response")
                        }
                        override fun onError(e: Exception) {
                            Log.d(TAG, "onError: $e")
                        }
                    })
                Log.d(TAG, "sendRequestWithOkHttp: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}