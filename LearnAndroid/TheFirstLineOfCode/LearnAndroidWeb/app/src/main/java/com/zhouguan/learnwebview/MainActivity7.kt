package com.zhouguan.learnwebview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain7Binding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread

// 定义MainActivity7类，继承自AppCompatActivity
class MainActivity7 : AppCompatActivity() {
    // 使用延迟初始化属性mBinding，通过布局文件创建绑定对象
    private val mBinding by lazy {
        ActivityMain7Binding.inflate(layoutInflater)
    }

    // 在Activity创建时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘功能
        enableEdgeToEdge()
        // 设置内容视图为mBinding.root，并设置窗口内边距
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 设置发送请求按钮的点击监听器
        mBinding.sendRequestBtn.setOnClickListener {
            sendRequestWithOkHttp()
        }
    }

    // 发送请求的函数，使用OkHttp库
    private fun sendRequestWithOkHttp() {
        // 在后台线程中执行网络请求
        thread {
            try {
                val client = OkHttpClient() // 创建OkHttpClient实例
                val request = Request.Builder()
                    // 指定访问的服务器地址是本机
                    .url("https://apifoxmock.com/m2/4944163-4601795-default/254028059?apifoxApiId=254028059") // 假设这是一个JSON数据文件的URL
//                    .url("http://10.0.2.2/get_data.json") // 假设这是一个JSON数据文件的URL
                    .build() // 构建请求对象
                val response = client.newCall(request).execute() // 执行请求并获取响应
                val responseData = response.body?.string() // 从响应中获取字符串数据
                if (responseData != null) {
                    parseJSONWithJSONObject(responseData) // 解析JSON数据
                }
            } catch (e: Exception) {
                e.printStackTrace() // 打印异常堆栈跟踪信息
            }
        }
    }

    // 解析JSON数据的函数
    private fun parseJSONWithJSONObject(jsonData: String) {
        try {
            val jsonArray = JSONArray(jsonData) // 创建JSONArray对象
            // 遍历JSON数组
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i) // 获取JSON对象
                val id = jsonObject.getString("id") // 获取id字段
                val name = jsonObject.getString("name") // 获取name字段
                val version = jsonObject.getString("version") // 获取version字段
                Log.d("MainActivity", "id is $id") // 打印id
                Log.d("MainActivity", "name is $name") // 打印name
                Log.d("MainActivity", "version is $version") // 打印version
            }
        } catch (e: Exception) {
            e.printStackTrace() // 打印异常堆栈跟踪信息
        }
    }
}