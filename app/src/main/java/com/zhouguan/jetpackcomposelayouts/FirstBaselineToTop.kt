package com.zhouguan.jetpackcomposelayouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhouguan.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = {

}

@Composable
fun TextWithPaddingToBaseline() {
    JetpackComposeLayoutsTheme {
        Text(text = "Hi there", Modifier.padding(24.dp))
    }
}