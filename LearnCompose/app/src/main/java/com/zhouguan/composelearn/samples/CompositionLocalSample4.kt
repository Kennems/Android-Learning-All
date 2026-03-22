package com.zhouguan.composelearn.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val isStatic = true
var compositionLocalName = ""
val currentLocalColor = if (isStatic) {
    compositionLocalName = "StaticCompositionLocal"
    staticCompositionLocalOf { Color.Black }
} else {
    compositionLocalName = "DynamicCompositionLocal"
    compositionLocalOf { Color.Black }
}

var recomposeFlag = "Init"

@Composable
fun CompositionSample4(modifier: Modifier) {
    val (color, setColor) = remember {
        mutableStateOf(Color.Green)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = compositionLocalName)
        Spacer(Modifier.height(20.dp))

        CompositionLocalProvider(currentLocalColor provides color) {
            TaggedBox("Wrapper: $recomposeFlag", 400.dp, Color.Red) {
                TaggedBox("Wrapper: $recomposeFlag", 400.dp, currentLocalColor.current) {
                    TaggedBox("Wrapper: $recomposeFlag", 400.dp, Color.Yellow) {

                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                setColor(Color.Blue)
                recomposeFlag = "Recompose"
            }
        ) {
            Text(text = "Change Theme")
        }
    }
}

@Composable
fun TaggedBox(
    tag: String,
    size: Dp,
    background: Color,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .size(size)
            .background(color = background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tag)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

}