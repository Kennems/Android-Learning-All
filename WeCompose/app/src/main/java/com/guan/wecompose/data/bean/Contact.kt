package com.guan.wecompose.data.bean

import androidx.annotation.DrawableRes

data class Contact(
    val name: String,
    @DrawableRes val avatar: Int
)


data class ContactTool(
    val name: String,
    @DrawableRes val avatar: Int
)
