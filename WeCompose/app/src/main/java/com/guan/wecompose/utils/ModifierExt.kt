package com.guan.wecompose.utils

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.chatDot(show: Boolean, color: Color) =
    drawWithContent {
        drawContent()
        if (show) {
            drawCircle(
                color,
                5.dp.toPx(),
                Offset(size.width - 1.dp.toPx(), 1.dp.toPx())
            )
        }
    }

fun Modifier.newUnRead(show: Boolean, color: Color) =
    drawWithContent {
        drawContent()
        if (show) {
            drawCircle(
                color,
                10.dp.toPx(),
                Offset( 25.dp.toPx(), 5.dp.toPx())
            )
        }
    }
