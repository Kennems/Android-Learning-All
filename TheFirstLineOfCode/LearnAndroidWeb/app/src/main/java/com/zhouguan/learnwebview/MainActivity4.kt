package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain4Binding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * OkHttp 使用
 */
class MainActivity4 : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityMain4Binding.inflate(layoutInflater)
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

        mBinding.sendRequestBtn.setOnClickListener {
            sendRequestWithOkHttp()
        }
    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://apifoxmock.com/m1/4944163-4601795-default/kotlinstudyserver/user")
                    .build()

                val response = client
                    .newCall(request)
                    .execute()

                val jsonObject = JSONObject(response.body?.string())

                Log.d("Kennem", "sendRequestWithOkHttp: $jsonObject")

                // 访问数据
                val personName: String = jsonObject.getString("name")
                val personAddress: String = jsonObject.getString("address")
                jsonObject?.let {
                    val showData = "\t 姓名 : ${personName} \n" +
                            "\t 地址 : $personAddress \n"
                    showResponse(showData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun showResponse(response: String) {
        runOnUiThread {
            mBinding.responseText.text = response
        }
    }

}