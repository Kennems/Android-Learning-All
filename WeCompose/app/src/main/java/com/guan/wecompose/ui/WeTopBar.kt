package com.guan.wecompose.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guan.wecompose.R
import com.guan.wecompose.ui.theme.WeComposeTheme
import com.guan.wecompose.ui.viewModel.WeViewModel

@Composable
fun WeTopBar(title: String, onBack: (() -> Unit)? = null) {
//    val viewModel: WeViewModel = viewModel()
    val viewModel: WeViewModel = viewModel(LocalActivity.current as ViewModelStoreOwner)

    Box(
        Modifier
            .background(WeComposeTheme.colors.background)
            .statusBarsPadding()
            .height(40.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back",
                        tint = WeComposeTheme.colors.icon,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // 占位保持对称
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { viewModel.switchTheme() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_palette),
                    contentDescription = "Shift to palette",
                    tint = WeComposeTheme.colors.icon,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // 标题文本居中显示
        Text(
            text = title,
            color = WeComposeTheme.colors.textPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WeTopBarPreview() {
    WeTopBar(title = "WeCompose")
}

