package com.zhouguan.composelearn

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.material3.LocalContentColor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun JetPackComposeLayouts() {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colorScheme.surface)
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
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
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
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ) {
                Text(
                    text = "3 minutes ago",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JetPackComposeLayoutsPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            JetPackComposeLayouts()
        }
    }
}