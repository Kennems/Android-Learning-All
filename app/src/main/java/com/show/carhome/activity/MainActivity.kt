package com.show.carhome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.show.carhome.R
import com.show.carhome.adapter.CarBrandAdapter
import com.show.carhome.databinding.ActivityMainBinding
import com.show.carhome.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * MainActivity 是应用的主界面活动，负责显示汽车品牌列表并处理用户交互。
 * 通过 ViewModel 提供数据，使用 RecyclerView 显示数据，并处理加载状态。
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * 使用 ActivityMainBinding 类进行视图绑定，简化对 UI 组件的访问。
     * 使用 lazy 委托延迟初始化绑定对象，确保只在需要时才初始化。
     */
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * 通过 viewModels 委托获取 ViewModel 实例，确保 ViewModel 与 Activity 的生命周期一致。
     */
    private val mViewModel: MainViewModel by viewModels()

    /**
     * 使用 lazy 委托延迟初始化 CarBrandAdapter 实例，避免在未使用时创建对象。
     */
    private val mCarBrandAdapter by lazy {
        CarBrandAdapter(this)
    }

    /**
     * Activity 的创建和初始化方法。
     *
     * @param savedInstanceState 以前保存的状态，用于恢复之前的状态
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘模式，以允许应用内容扩展到屏幕的边缘
        enableEdgeToEdge()
        setContentView(mBinding.root) // 设置内容视图为绑定的根视图

        // 设置 ViewCompat 的 OnApplyWindowInsetsListener 以处理窗口插图（如状态栏和导航栏的内边距）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // 获取系统栏的插图
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // 设置视图的内边距，以避免被系统栏遮挡
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // 返回原始的 WindowInsets 对象
        }

        // 将 CarBrandAdapter 设置为 RecyclerView 的适配器
        mBinding.recyclerView.adapter = mCarBrandAdapter

        // 观察 ViewModel 的数据变化并提交到适配器
        mViewModel.data.observe(this) {
            mCarBrandAdapter.submitData(lifecycle, it)
            mBinding.swipeRefresh.isEnabled = false // 禁用下拉刷新
        }

        // 监听适配器的加载状态，并根据状态更新下拉刷新的显示
        lifecycleScope.launchWhenCreated {
            mCarBrandAdapter.loadStateFlow.collectLatest { state ->
                // 如果加载状态为 Loading，则显示下拉刷新进度条
                mBinding.swipeRefresh.isRefreshing = state.refresh is LoadState.Loading
            }
        }
    }
}