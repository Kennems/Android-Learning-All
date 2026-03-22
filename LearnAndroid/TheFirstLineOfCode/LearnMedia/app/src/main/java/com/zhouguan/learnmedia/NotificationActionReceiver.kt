package com.zhouguan.learnmedia

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import android.widget.Toast

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACTION_BUTTON_CLICK" -> {
                // 动作按钮点击事件
                Toast.makeText(context, "动作按钮已点击", Toast.LENGTH_SHORT).show()
            }

            "ACTION_VIEW_BIG_PICTURE" -> {
                Toast.makeText(context, "查看大图动作点击", Toast.LENGTH_SHORT).show()
            }

            "ACTION_QUICK_REPLY" -> {
                // 处理直接回复的内容
                val replyText =
                    RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
                Toast.makeText(context, "回复内容：$replyText", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
