package com.zhouguan.learnglide

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.zhouguan.learnglide.ViewModel.MainViewModel
import com.zhouguan.learnglide.adapter.GlideFeatureAdapter
import com.zhouguan.learnglide.data.GlideFeature
import com.zhouguan.learnglide.databinding.ActivityMainBinding
import com.zhouguan.learnglide.transformation.ColorAdjustTransformation
import com.zhouguan.learnglide.transformation.ColorFilterTransformation
import jp.wasabeef.glide.transformations.BlurTransformation

class MainActivity : AppCompatActivity() {

    private val imageUrl = "https://www.bing.com/rp/6eCXduaLtTjxr74wpHJ534TrTiE.jpg"  // 示例图片URL
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val features = listOf(
            GlideFeature("普通加载", "展示普通图片加载") {
                Glide.with(this)
                    .load(imageUrl)
                    .into(it)
            },
            GlideFeature("圆形裁剪", "裁剪成圆形") {
                Glide.with(this)
                    .load(imageUrl)
                    .transform(CircleCrop())
                    .into(it)
            },
            GlideFeature("圆角裁剪", "裁剪成圆角") {
                Glide.with(this)
                    .load(imageUrl)
                    .transform(RoundedCorners(30)) // 圆角裁剪，半径 30
                    .into(it)
            },
            GlideFeature("调整大小 实现缩略图", "调整原图为 50 * 50 大小") {
                Glide.with(this)
                    .load(imageUrl)
                    .override(50, 50)
                    .into(it)
            },
            GlideFeature("加载占位图", "加载占位图，加载失败时显示") {
                Glide.with(this)
                    .load("fake url")
                    .placeholder(R.drawable.placeholder)
                    .into(it)
            },
            GlideFeature("加载失败图", "加载失败时显示的图") {
                Glide.with(this)
                    .load("fake url")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .into(it)
                /** 加载图片顺序 ：
                 * 1. 占位图， 加载图片过程中用占位图
                 *       加载成功后， 用加载成功的图片
                 *       加载失败时， 用加载失败图
                 */
            },
            GlideFeature("加载GIF", "加载GIF") {
                Glide.with(this)
                    .asGif() // 显式指定加载GIF
                    .load("https://www.toolnb.com/Public/tools-images/featureQrcode/qrcode-3.gif")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error_image)
                    .into(it)
            },

            GlideFeature("加载视频的一帧", "加载视频的一帧") {
                Glide.with(this)
                    .load("https://s97-ious.freeconvert.com/task/67848a7be3724baef7705c29/Cat2.mp4")
                    .placeholder(R.drawable.loadinggif)
                    .frame(13)
                    .error(R.drawable.error_image)
                    .into(it)
            },

            GlideFeature("加载失败后的逻辑", "模拟加载失败后的逻辑") {
                Glide.with(this)
                    .load("fake url")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // 处理失败逻辑
                            Log.e("Kennem", "加载失败: ${e?.message}")
                            Toast.makeText(this@MainActivity, "加载失败", Toast.LENGTH_SHORT).show()
                            return false // 返回 false 以继续显示错误占位符
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // 图片加载成功逻辑
                            Toast.makeText(this@MainActivity, "加载成功", Toast.LENGTH_SHORT).show()
                            return false // 返回 false 以继续正常显示图片
                        }
                    })
                    .into(it)
            },
            GlideFeature("图片模糊效果 ", "图片模糊效果") {
                Glide.with(this)
                    .load(imageUrl)
                    .transform(BlurTransformation(25, 3)) // 25为模糊半径，3为采样倍数
                    .placeholder(R.drawable.loadinggif)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(it)
            },
            // 可以改变图片 亮度 对比度 饱和度 等参数
            GlideFeature("图片调优 ", "图片调优") {
                Glide.with(this)
                    .load(imageUrl)
                    .transform(ColorAdjustTransformation(1.2f, 1.5f, 0.8f)) // 调整亮度、对比度、饱和度
                    .into(it)
            },
            GlideFeature("色调调整 ", "色调调整") {
                Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions().transform(ColorFilterTransformation(Color.parseColor("#FF5733")))) // 应用色调滤镜
                    .into(it)
            },
            GlideFeature("渐变加载 ", "渐变加载") {
                Glide.with(this)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade()) // 渐变加载
                    .into(it)
            },

            )

        binding.viewPager.adapter = GlideFeatureAdapter(features)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // 自定义 Tab 的布局
            tab.setCustomView(R.layout.custom_tab)
        }.attach()
    }
}
