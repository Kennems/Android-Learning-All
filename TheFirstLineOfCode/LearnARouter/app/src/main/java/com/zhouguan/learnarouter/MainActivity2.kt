package com.zhouguan.learnarouter

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path = "/app/main2")  // 标记页面的路由地址
class MainActivity2 : BaseActivity() {
    companion object {
        const val TAG = "MainActivity2"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ${this.javaClass.simpleName}")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.btn_jump)

        btn.setOnClickListener {
            // 跳转到 MainActivity
            ARouter
                .getInstance()
                .build("/app/main")
                .navigation()
            Log.d("Kennem", "onCreate: Jump")

//            ARouter.getInstance().build("/app/detail")
//                .withString("name", "John")
//                .withInt("age", 25)
//                .navigation();
        }
    }
}