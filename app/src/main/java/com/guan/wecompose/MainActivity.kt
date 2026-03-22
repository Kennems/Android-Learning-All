package com.guan.wecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.guan.wecompose.ui.ChatDetail
import com.guan.wecompose.ui.ChatDetailPage
import com.guan.wecompose.ui.Home
import com.guan.wecompose.ui.HomePage
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.ui.viewModel.WeViewModel

class MainActivity : ComponentActivity() {
    val viewModel: WeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        lifecycleScope.launch {
//            viewModel.is
//        }

        setContent {
            WeMainScreen(viewModel)
        }
    }

    @Composable
    fun WeMainScreen(viewModel: WeViewModel = viewModel()) {
        WeComposeTheme(theme = viewModel.theme) {
            val navController = rememberNavController()
            // NavHost 绑定 NavController 和起始路由
            NavHost(
                navController = navController,
                startDestination = Home // 用字符串路由表示起始页面
            ) {
                composable<Home> {
                    HomePage(viewModel) { chat ->
                        Log.d("ZhouGuan", "WeMainScreen: Jump")
                        navController.navigate(ChatDetail(chat.friend.id))
                    }
                }
                composable<ChatDetail>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    val detail = it.toRoute<ChatDetail>() // 反序列化出参数
                    ChatDetailPage(viewModel, detail.userId)
                }
            }
        }
    }

}




