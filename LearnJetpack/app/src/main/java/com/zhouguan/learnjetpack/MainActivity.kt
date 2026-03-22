package com.zhouguan.learnjetpack

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.zhouguan.learnjetpack.ViewModel.MainViewModel
import com.zhouguan.learnjetpack.databinding.ActivityMainBinding
import com.zhouguan.learnjetpack.entity.Repository
import com.zhouguan.learnjetpack.entity.User

class MainActivity : AppCompatActivity() {

    private val sp: SharedPreferences by lazy {
        getPreferences(MODE_PRIVATE)
    }

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val countReserved by lazy {
        sp.getInt("count_reserved", 0)
    }


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(counterReserved = countReserved)
        ).get(MainViewModel(0)::class.java)
    }

    private val userIdLiveData = MutableLiveData<String>()
    val user: LiveData<User> = userIdLiveData.switchMap { userId ->
        Repository.getUser(userId)
    }
    fun getUser(userId: String) {
        userIdLiveData.value = userId
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        lifecycle.addObserver(MyObserver())

        mBinding.plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        mBinding.clearBtn.setOnClickListener {
            viewModel.clear()
        }
//        viewModel.counter.observe(this, Observer { count ->
//            mBinding.infoText.text = count.toString()
//        })

        viewModel.count.observe(this) { count ->
            mBinding.infoText.text = count.toString()
        }

        mBinding.getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.getUser(userId)
        }

    }

    override fun onPause() {
        super.onPause()
        sp.edit()
            .putInt("count_reserved", viewModel.count.value ?: 0)
            .apply() // 或 .commit()
    }
}