package com.zhouguan.composelearn

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 测量所有子元素并保存测量结果
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        // 计算总高度
        val totalHeight = placeables.sumOf { it.height }
        // 布局的尺寸可以根据需求调整，这里使用最大宽度和计算出的总高度
        layout(constraints.maxWidth, totalHeight) {
            var yPosition = 0
            placeables.forEach { placeable ->
                // 正确调用当前子元素的放置方法
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyOwnColumnSample() {
    ComPoseLearnTheme {
        MyOwnColumn(Modifier.padding(16.dp)) {
            Text("MyOwnColumn")
            Text(text = "places items")
            Text(text = "vertically")
            Text(text = "We've done it by hand!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyOwnColumnPreview() {
    ComPoseLearnTheme {
        MyOwnColumn(Modifier.padding(16.dp)) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically")
            Text("We've done it by hand!")
        }
    }
}
