package com.guan.wecompose.data.bean

import androidx.annotation.DrawableRes
import com.guan.wecompose.R

class User(
    val id: String,
    val name: String,
    @DrawableRes val avatar: Int
) {
    companion object {
        val Me: User = User("0", "Kennem", R.drawable.avatar_rengwuxian)
    }
}