package com.guan.wecompose.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guan.wecompose.data.bean.Chat
import com.guan.wecompose.data.bean.Msg
import com.guan.wecompose.data.bean.User
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.utils.chatDot

@Composable
fun ChatList(chats: List<Chat>, onChatClick: ((Chat) -> Unit)? = null) {
    Column {
        WeTopBar(
            title = "WeCompose"
        )
        Box(
            Modifier
                .background(WeComposeTheme.colors.background)
                .fillMaxSize()
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(chats) { index, chat ->
                    if (index > 0) {
                        HorizontalDivider(
                            Modifier.padding(start = 68.dp),
                            color = WeComposeTheme.colors.divider,
                            thickness = 0.8f.dp
                        )
                    }
                    ChatItems(chat, Modifier.clickable {
                        if (onChatClick != null) {
                            onChatClick(chat)
                            Log.d("ZhouGuan", "ChatList: jump")
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun ChatItems(chat: Chat, modifier: Modifier) {
    Row(
        modifier
            .fillMaxSize()
            .background(WeComposeTheme.colors.listItem)
    ) {
        Image(
            painterResource(chat.friend.avatar), contentDescription = chat.friend.name,
            Modifier
                .padding(4.dp)
                .size(48.dp)
                .chatDot(chat.msgs.last().read, WeComposeTheme.colors.badge)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(Modifier.weight(1f)) {
            Text(
                chat.friend.name,
                modifier = Modifier.padding(4.dp),
                color = WeComposeTheme.colors.textPrimary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                chat.msgs.last().text,
                modifier = Modifier.padding(4.dp),
                color = WeComposeTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            chat.msgs.last().time,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            color = WeComposeTheme.colors.textSecondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview
@Composable
fun ChatListPreview() {
    ChatList(
        listOf(
            Chat(
                User.Me,
                mutableListOf(Msg(User.Me, "hello", System.currentTimeMillis().toString()))
            ),
            Chat(
                User.Me,
                mutableListOf(Msg(User.Me, "hello", System.currentTimeMillis().toString()))
            ),
            Chat(
                User.Me,
                mutableListOf(Msg(User.Me, "hello", System.currentTimeMillis().toString()))
            ),
            Chat(
                User.Me,
                mutableListOf(Msg(User.Me, "hello", System.currentTimeMillis().toString()))
            ),
            Chat(
                User.Me,
                mutableListOf(Msg(User.Me, "hello", System.currentTimeMillis().toString()))
            ),
        )
    )
}

