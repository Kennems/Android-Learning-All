package com.guan.wecompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guan.wecompose.R
import com.guan.wecompose.data.bean.NavItem
import com.guan.wecompose.ui.viewModel.WeViewModel
import com.guan.wecompose.utils.chatDot

@Composable
fun ChatNavBar(selected: Int, onTabSelected: (Int) -> Unit) {
    val viewModel: WeViewModel = viewModel()
    val items = viewModel.navTabs

    Row(Modifier.background(WeComposeTheme.colors.bottomBar)) {
        items.forEachIndexed { index, item ->
            val iconRes = if (selected == index) item.selectedIcon else item.unselectedIcon
            val tint =
                if (selected == index) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon

            TabItem(
                iconRes,
                item.title,
                tint,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            onTabSelected(index)
                        }
                    )
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
fun TabItem(iconId: Int, title: String, tint: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 10.dp, bottom = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(iconId),
            contentDescription = title,
            modifier = Modifier
                .size(24.dp)
                .chatDot(true, WeComposeTheme.colors.badge),
            tint = tint
        )
        Text(
            title,
            fontSize = 11.sp,
            color = tint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabItem() {
    WeComposeTheme(WeComposeTheme.Theme.Dark) {
        TabItem(R.drawable.ic_chat_filled, "Chat", green3)
    }
}

@Preview(showBackground = true)
@Composable
fun ChatNavBarPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }
    WeComposeTheme(WeComposeTheme.Theme.Light) {
        ChatNavBar(selectedTab, onTabSelected = {
            selectedTab = it
        })
    }
}


@Preview(showBackground = true)
@Composable
fun ChatNavBarDarkPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }
    WeComposeTheme(WeComposeTheme.Theme.Dark) {
        ChatNavBar(selectedTab, onTabSelected = {
            selectedTab = it
        })
    }
}


@Preview(showBackground = true)
@Composable
fun ChatNavBarNewYearPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }

    WeComposeTheme(WeComposeTheme.Theme.NewYear) {
        ChatNavBar(selectedTab, onTabSelected = {
            selectedTab = it
        })
    }
}