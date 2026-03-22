package com.zhouguan.learnglide.data

import android.widget.ImageView

data class GlideFeature(
    val title: String,
    val description: String,
    val action: (ImageView) -> Unit
)
