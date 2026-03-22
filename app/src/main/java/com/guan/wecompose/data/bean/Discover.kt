package com.guan.wecompose.data.bean

data class DiscoverItem(
    val title: String,
    val leftIcon: Int,
    val rightButtonIcon: Int,
    val rightIcon: Int? = null,
    val rightIconTint: String? = null,
)
