package com.show.carhome.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.show.carhome.R

@BindingAdapter("bindingAvator")
fun bindingAvator(imageView: ImageView, url: String?) {
    Log.d("Kennem", "loading image $url")

    if (url.isNullOrEmpty()) {
        // 处理url为空或空字符串的情况，例如显示默认图片
        imageView.setImageResource(R.drawable.place_holder)
    } else {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.place_holder)
        }
    }
}
