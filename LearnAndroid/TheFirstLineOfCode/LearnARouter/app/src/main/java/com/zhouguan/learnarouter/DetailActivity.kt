package com.zhouguan.learnarouter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path = "/app/detail")
class DetailActivity : BaseActivity() {

    // String 类型字段建议使用 lateinit（非空初始化）
    @Autowired
    lateinit var name: String

    // 基本类型需要设置默认值
    @Autowired
    @JvmField
    public var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 参数注入
        ARouter
            .getInstance()
            .inject(this)

        // 使用参数（示例）
        println("姓名：$name, 年龄：$age")
    }
}