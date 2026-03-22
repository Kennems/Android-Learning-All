package com.show.learnmaterialdesign.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var mToast: Toast? = null
    fun show(context: Context, msg: String) {
        if (mToast != null) {
            mToast?.cancel()
        }
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}