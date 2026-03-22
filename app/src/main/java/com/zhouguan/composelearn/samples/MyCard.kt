package com.zhouguan.composelearn.samples

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Elevations(val card: Dp = 0.dp)

val LocalElevations = compositionLocalOf { Elevations() }

object CardElevation {
    val high: Elevations
        get() = Elevations(card = 10.dp)
    val low: Elevations
        get() = Elevations(card = 0.05.dp)
}

@Composable
fun MyCard(
    elevation: Dp = LocalElevations.current.card,
    backgroundColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        modifier = Modifier.size(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        content = content
    )
}