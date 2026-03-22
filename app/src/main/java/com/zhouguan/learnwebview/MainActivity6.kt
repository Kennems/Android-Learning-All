package com.zhouguan.learnwebview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnwebview.databinding.ActivityMain6Binding
import com.zhouguan.learnwebview.handler.ContentHandler
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

/**
 * Sax 解析 xml
 */
class MainActivity6 : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityMain6Binding.inflate(layoutInflater)
    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    // 指定访问的服务器地址是计算机本机
//                    .url("http://10.0.2.2/get_data.xml")
                    .url("https://apifoxmock.com/m2/4944163-4601795-default/254025004")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    parseXMLWithSAX(responseData)
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseXMLWithSAX(xmlData: String) {
        try {
            val factory = SAXParserFactory.newInstance()
            val xmlReader = factory.newSAXParser().xmlReader
            val handler = ContentHandler()
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.contentHandler = handler
            // 开始执行解析
            xmlReader.parse(InputSource(StringReader(xmlData)))

        } catch (e: Exception) {
            e.printStackTrace()
        }
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
}