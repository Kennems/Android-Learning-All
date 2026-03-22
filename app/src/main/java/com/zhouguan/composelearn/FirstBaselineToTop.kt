package com.zhouguan.composelearn

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("SuspiciousModifierThen")
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val firstBaseLine = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // 设置元素位置
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun TextWithPaddingToBaseline() {
    ComPoseLearnTheme {
        Text(
            text = "Hi there",
            Modifier
                .firstBaselineToTop(36.dp)
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComPoseLearnTheme {
        TextWithPaddingToBaseline()
    }
}