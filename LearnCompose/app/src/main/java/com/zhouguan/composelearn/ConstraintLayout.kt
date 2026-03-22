@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.zhouguan.composelearn

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme


@Composable
fun ConstraintsLayoutContent() {
    ConstraintLayout {
        val (button, text) = createRefs()
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        ) {
            Text("Button")
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
            Text("Button 1")
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
            Text("Button 2")
        }
    }
}

@Composable
fun LargeLayoutContent() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(0.5f)
        Text(
            text = "This is a very long text that should wrap automatically in the ConstraintLayout",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.wrapContent
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
                top.linkTo(parent.top, margin = margin)
            }
        ) {
            Text("Button")
        }

        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = margin)
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout2(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val constraints = if (this.maxWidth < this.maxHeight) { // 显式 this
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 160.dp)
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ConstraintsLayoutPreview() {
    ComPoseLearnTheme {
        // 可以选择你想预览的布局
        DecoupledConstraintLayout2(modifier = Modifier)
        // 如果想依次预览其他布局，可分别调用
        // ConstraintsLayoutContent()
        // ConstraintsLayoutContent2()
        // LargeLayoutContent()
        // DecoupledConstraintLayout()
    }
}