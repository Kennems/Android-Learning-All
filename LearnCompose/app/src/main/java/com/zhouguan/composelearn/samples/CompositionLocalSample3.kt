package com.zhouguan.composelearn.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@Composable
fun CompositionSample3(modifier: Modifier) {
    Column(modifier = modifier) {
        CompositionLocalProvider(LocalElevations provides CardElevation.low) {
            MyCard(backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)) {
            }
        }

        CompositionLocalProvider(LocalElevations provides CardElevation.high) {
            MyCard(backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)) {
            }
        }

    }
}
