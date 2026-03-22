package com.zhouguan.composelearn.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zhouguan.composelearn.R

@Composable
fun CompositionSample2(modifier: Modifier) {
    MaterialTheme {
        Column(modifier = modifier) {
            Text(text = "User Material Design1")
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                // 使用最近的 CompositionLocal 的值
                Text(text = "User Material Design2")
                Text(text = "User Material Design3")
            }
            DescendantExample()
            FruitText(10)
            FruitText(1)
            FruitText(100)
            FruitText(100)
            FruitText(1000)
            FruitText(100)
        }
    }
}

@Composable
fun DescendantExample() {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.38f
        )
    ) {
        Text(text = "User Material Design4")
    }
}

@Composable
fun FruitText(fruitSize: Int) {
    val resources = LocalContext.current.resources!!
    val fruitText = resources.getQuantityText(R.plurals.fruit_title, fruitSize)
    Text(text = "$fruitSize $fruitText")
}