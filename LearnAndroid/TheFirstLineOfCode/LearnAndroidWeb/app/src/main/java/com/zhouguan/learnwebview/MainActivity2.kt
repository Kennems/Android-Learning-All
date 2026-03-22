package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain2Binding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * HTTPURLConnection 使用
 */
class MainActivity2 : AppCompatActivity() {
    companion object {
        const val TAG = "Kennem"
    }

    private val mBinding by lazy { ActivityMain2Binding.inflate(layoutInflater) }

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
            sendRequestWithHttpURLConnection()
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val url =
                    URL("https://apifoxmock.com/m1/4944163-4601795-default/kotlinstudyserver/user")

                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.requestMethod = "GET"
                // POST 请求
//                connection.requestMethod = "POST"

                // 添加 GET 请求参数
//                val output = DataOutputStream(connection.outputStream)
//                output.writeBytes("username=admin&password=123456")

                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))

                val response = StringBuilder()
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }

//                showResponse(response.toString())

                val jsonString = response.toString()
                val jsonObject = JSONObject(jsonString)
                // 访问数据
                val personName: String = jsonObject.getString("name")
                val personAddress: String = jsonObject.getString("address")

                showResponse(
                    "\t 姓名 : $personName \n" +
                            "\t 地址 : $personAddress \n"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            mBinding.responseText.text = response
        }
    }
}
