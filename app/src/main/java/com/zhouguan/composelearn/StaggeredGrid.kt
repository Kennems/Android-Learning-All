package com.zhouguan.composelearn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme
import kotlin.math.max

val topics = listOf(
    "Arts * Crafts",
    "Health * Wellness",
    "Food * Drinks",
    "Travel * Adventure",
    "Technology * Gadgets",
    "Fashion * Beauty",
    "Home * Garden",
    "Sports * Fitness",
    "Music * Entertainment",
    "Education * Learning",
    "Business * Finance",
    "Science * Nature",
    "Photography * Film",
    "History * Culture",
    "Parenting * Family",
    "Self * Improvement",
    "Books * Literature",
    "Pets * Animals",
    "Gaming * Esports",
    "Social * Community"
)

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        val width = rowWidths.maxOrNull() ?: constraints.maxWidth
        val height = rowHeights.sum()

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            // 设置每一个元素的坐标
            placeables.forEachIndexed { index, placeable ->
                // index: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
                // rows: 3
                // row: 0, 1, 2
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun StaggeredGridBodyContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.LightGray)
            .padding(16.dp)
            .horizontalScroll(rememberScrollState()),
        content = {
            StaggeredGrid(modifier = Modifier) {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun StaggeredGridBodyContentPreview() {
    ComPoseLearnTheme() {
        // 给外层加一个背景，方便预览
        StaggeredGridBodyContent(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        )
    }
}