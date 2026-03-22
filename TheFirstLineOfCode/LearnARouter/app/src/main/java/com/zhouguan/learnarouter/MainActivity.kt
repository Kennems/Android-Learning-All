package com.zhouguan.learnarouter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path = "/app/main")  // 标记页面的路由地址
class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ${this.javaClass.simpleName}")
        setContentView(R.layout.activity_main)

        val btn_jump = findViewById<Button>(R.id.btn_jump)

        btn_jump.setOnClickListener{
            ARouter.getInstance().build("/app/main2").navigation()
        }
    }
}