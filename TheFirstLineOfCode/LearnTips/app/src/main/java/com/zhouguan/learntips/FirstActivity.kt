package com.zhouguan.learntips

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learntips.databinding.ActivityFirstBinding
import com.zhouguan.learntips.entity.Person

class FirstActivity : AppCompatActivity() {

    private val mBinding
            by lazy { ActivityFirstBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val person = Person("Tom", 20)
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("person_data", person)

        mBinding.apply {
            btn.setOnClickListener {
                startActivity(intent)
            }
        }
    }
}