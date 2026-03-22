package com.zhouguan.jetpackcomposelayouts

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TwoTexts(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            text = "Hi",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = "There",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}