package com.guan.wecompose.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guan.wecompose.data.bean.Msg
import com.guan.wecompose.data.bean.User
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.ui.viewModel.WeViewModel
import kotlinx.serialization.Serializable


@Serializable
class ChatDetail(val userId: String)

@Composable
fun ChatDetailPage(viewModel: WeViewModel, userId: String) {
    Log.d("ZhouGuan", "ChatDetailPage: $userId")
    val chat = viewModel.chats.find { it.friend.id == userId }!!
    Box(
        Modifier.background(WeComposeTheme.colors.chatPage)
            .fillMaxSize()

    ) {
        Column(
        ) {
            val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            WeTopBar(chat.friend.name) {
                backDispatcher?.onBackPressed()
            }
            LazyColumn {
                itemsIndexed(chat.msgs) { index, msg ->
                    ChatItem(msg)
                }
            }
        }
    }
}

@Composable
fun ChatItem(msg: Msg) {
    val isReceive = (msg.from != User.Me)
    if (isReceive) {
        Row(
            modifier = Modifier.fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.Start, // 水平方向左对齐
            verticalAlignment = Alignment.CenterVertically // 垂直方向居中，可选
        ) {
            Column {
                Image(
                    painter = painterResource(msg.from.avatar),
                    contentDescription = msg.from.name,
                    Modifier
                        .align(Alignment.Start)
                        .size(36.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            Column {
                Box(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(WeComposeTheme.colors.listItem)
                ) {
                    Text(msg.text)
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End, // 水平方向左对齐
            verticalAlignment = Alignment.CenterVertically // 垂直方向居中，可选
        ) {
            Column {
                Box(Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(WeComposeTheme.colors.listItem)) {
                    Text(
                        msg.text
                    )
                }
            }
            Column {
                Image(
                    painter = painterResource(msg.from.avatar),
                    contentDescription = msg.from.name,
                    Modifier
                        .align(Alignment.End)
                        .size(36.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
//    ChatItem(
//        Msg(
//            User("gaolaoshi", "高老师", R.drawable.avatar_gaolaoshi),
//            "锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午锄禾日当午",
//            "14:20"
//        )
//    )
    ChatItem(Msg(User.Me, "汗滴禾下土", "14:20"))
}

@Preview
@Composable
fun ChatDetailPagePreview() {
    val viewModel: WeViewModel = viewModel()
    ChatDetailPage(viewModel, "gaolaoshi")
}