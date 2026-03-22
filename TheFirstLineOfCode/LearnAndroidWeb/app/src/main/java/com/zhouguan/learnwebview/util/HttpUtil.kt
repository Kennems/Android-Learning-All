package com.zhouguan.learnwebview.util

import com.zhouguan.learnwebview.`interface`.HttpCallbackListener
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpUtil {
    fun sendHttpRequest(address: String, httpCallbackListener: HttpCallbackListener): String {
        var connection: HttpURLConnection? = null
        try {
            val response = StringBuilder()
            val url = URL(address)
            connection = url.openConnection() as HttpURLConnection

            connection.connectTimeout = 8000
            connection.readTimeout = 8000

            val input = connection.inputStream
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    response.append(it)
                }
            }

            // 调用 onFinish 回调
            httpCallbackListener.onFinish(response.toString())
            return response.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            // 调用 onError 回调
            httpCallbackListener.onError(e)
            return ""
        } finally {
            connection?.disconnect()
        }
    }

    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
    }
}