package com.guan.jetpackcomposestate.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhouguan.composelearn.R
import com.zhouguan.composelearn.databinding.ActivityHelloComposeStateBinding

class HelloComposeStateActivity : ComponentActivity() {

    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.etTitle.doAfterTextChanged {
            binding.tvTitle.text = "Hello! ${it.toString()}"
        }

    }
}

class HelloActivityViewModel : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

}

class HelloComposeStateActivityWithViewModel : ComponentActivity() {

    private val binding by lazy {
        ActivityHelloComposeStateBinding.inflate(layoutInflater)
    }

    private val helloViewModel by viewModels<HelloActivityViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.etTitle.doAfterTextChanged {
            helloViewModel.onNameChanged(it.toString())
        }

        helloViewModel.name.observe(this) { name ->
            binding.tvTitle.text = "Hello! $name"
        }
    }
}