package com.zhouguan.learnmedia

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhouguan.learnmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var manager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // 处理系统边缘适配
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化 NotificationManager
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 创建通知渠道
        createNotificationChannels()
        createHeadsUpChannel()
        createSoundVibrateChannel()

        // 请求通知权限（Android 13 及以上需要动态申请）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
            }
        }

        // 点击事件 - 基本通知（默认样式）
        mBinding.btnBasicNotification.setOnClickListener {
            sendBasicNotification()
        }

        // 点击事件 - BigTextStyle 通知
        mBinding.btnBigTextNotification.setOnClickListener {
            sendBigTextNotification()
        }

        // 点击事件 - BigPictureStyle 通知
        mBinding.btnBigPictureNotification.setOnClickListener {
            sendBigPictureNotification()
        }

        // 点击事件 - InboxStyle 通知（多行显示）
        mBinding.btnInboxNotification.setOnClickListener {
            sendInboxStyleNotification()
        }

        // 点击事件 - MessagingStyle 通知（对话消息）
        mBinding.btnMessagingNotification.setOnClickListener {
            sendMessagingStyleNotification()
        }

        mBinding.btnHeadsUpNotification.setOnClickListener {
            sendHeadsUpNotification()
        }

        mBinding.btnSoundVibrateNotification.setOnClickListener {
            sendSoundVibrateNotification()
        }

        // 点击事件 - 取消所有通知
        mBinding.btnCancelNotification.setOnClickListener {
            manager.cancelAll()
        }
    }

    /**
     * 创建通知渠道（Android 8.0+ 必须设置通知渠道）
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 普通通知渠道（低优先级）
            val normalChannel = NotificationChannel(
                "normal", "Normal Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(normalChannel)

            // 重要通知渠道（高优先级，带弹窗）
            val importantChannel = NotificationChannel(
                "important", "Important Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(importantChannel)
        }
    }

    /**
     * 发送基本通知
     */
    private fun sendBasicNotification() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, "normal")
            .setContentTitle("基本通知")
            .setContentText("这是一个基本通知")
            .setSmallIcon(R.drawable.boat)
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(101, notification)
    }

    /**
     * 发送 BigTextStyle 通知（长文本）
     */
    private fun sendBigTextNotification() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val bigText =
            "这是一个使用 BigTextStyle 的通知，展示了较长的文本内容。你可以在通知中展示详细的信息， " +
                    "例如新闻内容、邮件摘要或者其他需要展示更多文本的场景。"
        val notification = NotificationCompat.Builder(this, "normal")
            .setContentTitle("BigTextStyle通知")
            .setContentText("点击展开查看更多文本内容")
            .setSmallIcon(R.drawable.boat)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(102, notification)
    }

    /**
     * 发送 BigPictureStyle 通知（大图片）
     */
    private fun sendBigPictureNotification() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        // 使用项目资源作为图片示例
        val bigPic = BitmapFactory.decodeResource(resources, R.drawable.boat)
        val notification = NotificationCompat.Builder(this, "important")
            .setContentTitle("BigPictureStyle通知")
            .setContentText("点击查看大图")
            .setSmallIcon(R.drawable.boat)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(bigPic)
                    .setSummaryText("图片通知摘要")
            )
            .setLargeIcon(bigPic)
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(103, notification)
    }

    /**
     * 发送 InboxStyle 通知（多行内容）
     */
    private fun sendInboxStyleNotification() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        // 模拟多行消息内容
        val lines = listOf(
            "第一条消息内容",
            "第二条消息内容",
            "第三条消息内容",
            "第四条消息内容",
            "第五条消息内容"
        )
        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle("InboxStyle通知")
        for (line in lines) {
            inboxStyle.addLine(line)
        }
        val notification = NotificationCompat.Builder(this, "normal")
            .setContentTitle("InboxStyle通知")
            .setContentText("滑动展开查看更多内容")
            .setSmallIcon(R.drawable.boat)
            .setStyle(inboxStyle)
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(104, notification)
    }

    /**
     * 发送 MessagingStyle 通知（对话消息风格）
     */
    private fun sendMessagingStyleNotification() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // 创建发送者信息
        val user = Person.Builder().setName("自己").build()
        val personA = Person.Builder().setName("张三").build()
        val personB = Person.Builder().setName("李四").build()

        // 构建 MessagingStyle 对象
        val messagingStyle = NotificationCompat.MessagingStyle(user)
            .setConversationTitle("群聊消息")
            .addMessage("早上好，各位！", System.currentTimeMillis() - 1000 * 60, personA)
            .addMessage("大家注意交通安全！", System.currentTimeMillis(), personB)

        val notification = NotificationCompat.Builder(this, "important")
            .setSmallIcon(R.drawable.boat)
            .setStyle(messagingStyle)
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(105, notification)
    }

    private fun createHeadsUpChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建一个专门用于头顶通知的渠道，重要性需设置为 IMPORTANCE_HIGH
            val channel = NotificationChannel(
                "heads_up_channel", "Heads-up Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 250, 250)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendHeadsUpNotification() {
        // 点击通知后跳转到 NotificationActivity，同时设置自动取消
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // 为全屏提示（可选）配置 full screen intent（例如针对紧急情况）
        // 通常全屏 intent 会在锁屏状态时触发，所以要谨慎使用
        val fullScreenIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // 声音 URI（使用默认通知铃声）
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this, "heads_up_channel")
            .setContentTitle("头顶通知")
            .setContentText("这是一条悬浮在屏幕上方的紧急通知")
            .setSmallIcon(R.drawable.boat)
            .setPriority(NotificationCompat.PRIORITY_HIGH)         // Android 8.0 以下生效
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 250, 250, 250))             // 振动效果

        // 如果你希望在某些场景下即刻弹出界面（例如锁屏），可以添加 full screen intent：
        // 注意：full screen intent 可能只在锁屏状态下触发，并且仅限于非常紧急场景使用
        builder.setFullScreenIntent(fullScreenIntent, true)

        // 通过 NotificationManager 发送通知
        manager.notify(200, builder.build())
    }

    private fun createSoundVibrateChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "sound_vibrate_channel", "Sound & Vibrate Channel",
                NotificationManager.IMPORTANCE_HIGH // 重要性设置为高
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 250, 250) // 设置震动模式
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
            manager.createNotificationChannel(channel)
        }
    }


    private fun sendSoundVibrateNotification() {
        // 【步骤1】构建点击跳转的 PendingIntent
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // 【步骤2】创建 FullScreenIntent（可选，用于锁屏下立即弹出）
        // 注意：全屏通知仅在紧急场景下使用，且通常只在锁屏状态下触发
        val fullScreenIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // 【步骤3】获取默认通知铃声
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 【步骤4】构造通知
        val notification = NotificationCompat.Builder(this, "sound_vibrate_channel")
            .setContentTitle("声音与震动测试")
            .setContentText("测试声音、震动以及悬浮通知效果")
            .setSmallIcon(R.drawable.boat)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(fullScreenIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)   // 针对 Android 8.0 以下设置高优先级
            .setAutoCancel(true)
            .setSound(soundUri)                               // 设置声音
            .setVibrate(longArrayOf(0, 250, 250, 250))         // 设置震动模式（单位：毫秒）
            .build()

        // 【步骤5】发送通知
        manager.notify(300, notification)
    }
}
