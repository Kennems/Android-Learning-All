package com.zhouguan.jetpackcomposelayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ContentAlpha
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalContentAlpha
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun JetPackComposeLayouts() {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
            .padding(16.dp)
        // 添加状态栏的 padding
    ) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .fillMaxWidth(),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.boat),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)

        ) {
            Text(
                text = "Alfred Sisely",
                fontWeight = FontWeight.Bold
            ) // 设置字体加粗
            // 前缀表达式
            // 调整透明度
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "3 minutes ago",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}