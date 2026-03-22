package com.guan.wecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.guan.wecompose.R
import com.guan.wecompose.data.bean.DiscoverItem
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.utils.newUnRead


@Composable
fun DiscoverList() {
    Column(Modifier.fillMaxSize()) {
        DiscoverTopBar()
        DiscoverListItem(
            DiscoverItem(
                "发现",
                R.drawable.ic_moments,
                R.drawable.ic_arrow_more,
                R.drawable.avatar_gaolaoshi
            )
        )
        DiscoverSpacer()

        DiscoverListItem(
            DiscoverItem(
                "视频号",
                R.drawable.ic_channels,
                R.drawable.ic_arrow_more,
                R.drawable.avatar_diuwuxian,
                "赞过"
            )
        )
        DiscoverSpacer()
        DiscoverListItem(
            DiscoverItem(
                "看一看",
                R.drawable.ic_ilook,
                R.drawable.ic_arrow_more,
            )
        )
        DiscoverHorizontalDivider()
        DiscoverListItem(
            DiscoverItem(
                "搜一搜",
                R.drawable.ic_isearch,
                R.drawable.ic_arrow_more,
            )
        )
        DiscoverSpacer()
        DiscoverListItem(
            DiscoverItem(
                "直播和附近",
                R.drawable.ic_nearby,
                R.drawable.ic_arrow_more,
            )
        )
    }
}

@Composable
fun DiscoverTopBar() {
    WeTopBar("发现")
}

@Composable
fun DiscoverListItem(item: DiscoverItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(WeComposeTheme.colors.listItem)
            .padding(8.dp), // 撑满宽度
        verticalAlignment = Alignment.CenterVertically // 垂直居中
    ) {
        Image(
            painterResource(item.leftIcon),
            contentDescription = item.title,
            modifier = Modifier
                .padding(4.dp)
                .size(28.dp)
        )

        Text(
            item.title,
            modifier = Modifier
                .padding(4.dp)
                .weight(1f)
                .newUnRead(true, WeComposeTheme.colors.badge),
            color = WeComposeTheme.colors.textPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )

        if (item.rightIcon != null) {
            Image(
                painterResource(item.rightIcon),
                contentDescription = item.title,
                modifier = Modifier
                    .padding(4.dp)
                    .size(26.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
        if (item.rightIconTint != null) {
            Text(
                item.rightIconTint,
                modifier = Modifier
                    .padding(end = 4.dp),
                color = WeComposeTheme.colors.textPrimary,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Image(
            painterResource(item.rightButtonIcon),
            contentDescription = item.title,
            modifier = Modifier
                .padding(4.dp)
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}


@Preview
@Composable
fun DiscoverItemPreview() {
    DiscoverListItem(
        DiscoverItem(
            "发现",
            R.drawable.ic_moments,
            R.drawable.ic_arrow_more,
            R.drawable.avatar_gaolaoshi,
            "赞过"
        )
    )
}

@Preview
@Composable
fun DiscoverItemPreviewNoRightIcon() {
    DiscoverList()
}

@Composable
fun DiscoverSpacer() {
    Spacer(Modifier.padding(6.dp))
}

@Composable
fun DiscoverHorizontalDivider() {
    HorizontalDivider(
        Modifier.padding(start = 68.dp),
        color = WeComposeTheme.colors.divider,
        thickness = 0.8f.dp
    )
}
