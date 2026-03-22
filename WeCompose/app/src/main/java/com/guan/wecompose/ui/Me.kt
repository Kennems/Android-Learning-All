package com.guan.wecompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guan.wecompose.R
import com.guan.wecompose.data.bean.MeItem
import com.guan.wecompose.data.bean.User
import com.guan.wecompose.ui.theme.WeComposeTheme

@Composable
fun Me() {
    Column(Modifier.fillMaxHeight()) {
        ProfileCard()
        MeSpacer()
        MeListItem(MeItem(R.drawable.ic_pay, "支付"))
        MeSpacer()
        MeListItem(MeItem(R.drawable.ic_collections, "收藏"))
        MeHorizontalDivider()
        MeListItem(MeItem(R.drawable.ic_photos, "朋友圈"))
        MeHorizontalDivider()
        MeListItem(MeItem(R.drawable.ic_cards, "卡包"))
        MeHorizontalDivider()
        MeListItem(MeItem(R.drawable.ic_stickers, "表情"))
        MeSpacer()
        MeListItem(MeItem(R.drawable.ic_settings, "设置"))
    }
}

@Composable
fun ProfileCard() {
    Row(
        modifier = Modifier
            .background(WeComposeTheme.colors.listItem)
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧头像
        Image(
            painter = painterResource(id = User.Me.avatar), // 替换为你的头像资源
            contentDescription = "Avatar",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 中间文本区域
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = User.Me.name,
                fontSize = 20.sp,
                color = WeComposeTheme.colors.textPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "抖信号：${User.Me.name}",
                fontSize = 14.sp,
                color = WeComposeTheme.colors.textSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 状态按钮
            OutlinedButton(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, WeComposeTheme.colors.onBackground),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = "+ 状态", fontSize = 14.sp,
                    color = WeComposeTheme.colors.textSecondary
                )
            }
        }

        // 右侧二维码按钮
        IconButton(onClick = { /* TODO 打开二维码 */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_qrcode), // 替换为你的二维码图标
                contentDescription = "QR Code",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }

        // 右侧二维码按钮
        IconButton(onClick = { /* TODO 打开二维码 */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_more), // 替换为你的二维码图标
                contentDescription = "QR Code",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun MeSpacer() {
    Spacer(modifier = Modifier.padding(6.dp))
}

@Composable
fun MeListItem(meItem: MeItem) {
    Row(
        Modifier
            .background(WeComposeTheme.colors.listItem)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Image(
            painterResource(meItem.resId),
            contentDescription = "pay",
            Modifier
                .padding(6.dp)
                .size(32.dp)
        )
        Text(
            meItem.title,
            color = WeComposeTheme.colors.textPrimary,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

@Preview
@Composable
fun MeListItemPreview() {
    MeListItem(MeItem(R.drawable.ic_pay, "支付"))
}

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard()
}

@Preview
@Composable
fun MePreview() {
    Me()
}

@Composable
fun MeHorizontalDivider() {
    HorizontalDivider(
        Modifier.padding(start = 68.dp),
        color = WeComposeTheme.colors.divider,
        thickness = 0.8f.dp
    )
}
