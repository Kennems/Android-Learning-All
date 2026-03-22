package com.guan.wecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.guan.wecompose.data.bean.Chat
import com.guan.wecompose.ui.theme.ChatNavBar
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.ui.viewModel.WeViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
fun HomePage(viewModel: WeViewModel, onChatClick : ((Chat) -> Unit)) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 }
    )
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                if (viewModel.selectedTab != page) {
                    viewModel.updateSelectedTab(page)
                }
            }
    }

    Column(
        Modifier
            .background(WeComposeTheme.colors.background)
            .statusBarsPadding()
    ) {
        HorizontalPager(pagerState, Modifier.weight(1f)) {
            when (it) {
                0 -> ChatList(viewModel.chats) {chat ->
                    onChatClick(chat)
                }
                1 -> ContactsList(viewModel.contacts, viewModel.contactTools)
                2 -> DiscoverList()
                3 -> Me()
            }
        }

        ChatNavBar(viewModel.selectedTab) { index ->
            if (viewModel.selectedTab == index) {
                return@ChatNavBar
            }
            viewModel.updateSelectedTab(index)
            scope.launch {
                pagerState.animateScrollToPage(index)
            }
        }
    }
}