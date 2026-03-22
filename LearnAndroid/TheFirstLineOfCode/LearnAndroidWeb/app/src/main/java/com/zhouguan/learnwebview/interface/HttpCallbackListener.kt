package com.zhouguan.learnwebview.`interface`

/**
 * Callback 接口
 */
interface HttpCallbackListener {
    fun onFinish(response: String)

    fun onError(e: Exception)
}