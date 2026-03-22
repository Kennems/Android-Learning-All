package com.zhouguan.learntips

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learntips.databinding.ActivitySecondBinding
import com.zhouguan.learntips.entity.Person
import com.zhouguan.learntips.util.LogUtil

class SecondActivity : AppCompatActivity() {
    private val mBinding
            by lazy { ActivitySecondBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val person = intent.getParcelableExtra("person_data") as Person?


//        Log.d("Kennem", person.toString())
        LogUtil.d("Kennem", person.toString())

        mBinding.apply {
            btn.setOnClickListener {
                startActivity(Intent(this@SecondActivity, FirstActivity::class.java))
            }
        }
    }
}