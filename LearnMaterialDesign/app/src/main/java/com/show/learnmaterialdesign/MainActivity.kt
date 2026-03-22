package com.show.learnmaterialdesign

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.show.learnmaterialdesign.adapter.FruitAdapter
import com.show.learnmaterialdesign.databinding.ActivityMainBinding
import com.show.learnmaterialdesign.entity.Fruit
import com.show.learnmaterialdesign.util.BarUtils
import com.show.learnmaterialdesign.util.ToastUtil
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
//    val fruits = mutableListOf(
//        Fruit("Apple", R.drawable.apple), Fruit("Banana",
//        R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon",
//        R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape",
//        R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry",
//        R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango",
//        R.drawable.mango))

    val fruits = mutableListOf(Fruit("Abs Draw", R.drawable.abs))

    val fruitList = ArrayList<Fruit>()

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        hideStatusBar()
        enableEdgeToEdge()
        setContentView(mBinding.root)

        fun isDarkTheme(context: Context): Boolean {
            val flag = context.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK
            return flag == Configuration.UI_MODE_NIGHT_YES
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        BarUtils.transparentStatusBar(this) // 需要沉浸状态栏，才能截屏至状态栏

        setSupportActionBar(mBinding.toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mBinding.navView.setCheckedItem(R.id.navCall)
        mBinding.navView.setNavigationItemSelectedListener {
            mBinding.drawerLayout.closeDrawers()
            true
        }

        mBinding.fab.setOnClickListener { view ->
//            ToastUtil.show(this, "You clicked Fab")
            Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        mBinding.recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.isNestedScrollingEnabled = true

        mBinding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        mBinding.swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    // 隐藏状态栏的方法
    private fun hideStatusBar() {
        // 使用 WindowInsets 设置沉浸式状态栏
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                ToastUtil.show(
                    this, "You clicked Home"
                )
                mBinding.drawerLayout.openDrawer(GravityCompat.START)
            }

            R.id.backup -> ToastUtil.show(
                this, "You clicked Backup"
            )

            R.id.delete -> ToastUtil.show(
                this, "You clicked Delete"
            )

            R.id.settings -> ToastUtil.show(
                this, "You clicked Settings"
            )
        }
        return true
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(100) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                mBinding.swipeRefresh.isRefreshing = false
            }
        }
    }

}