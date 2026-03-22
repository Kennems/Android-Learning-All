package com.zhouguan.jetpackcomposelayouts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintsLayoutContent() {
    ConstraintLayout {
        val (button, text) = createRefs()
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        ) {
            Text(text = "Button")

        }
        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}


@Composable
fun ConstraintsLayoutContent2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }


        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button1.bottom, margin = 16.dp)
                centerAround(button1.end)
            }
        )

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }


    }
}

@Composable
fun LargeLayoutContent() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(0.5f)
        Text(
            text = "This is long long long long long long long long long long long long long long long long long long long long long long long long  text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                // 自动换行
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout() {
    val margin = 16.dp
    ConstraintLayout {
        val (button, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button")
        }

        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = margin)
            })
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DecoupledConstraintLayout2(modifier: Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // 竖屏
        } else {
            decoupledConstraints(margin = 160.dp) // 横屏
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(
                text = "Text",
                modifier = Modifier.layoutId("text")
            )
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
        }
    }
}