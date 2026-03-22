package com.zhouguan.composelearn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutStudy() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutStudy")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding((8).dp)) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the LayoutStudy")
    }
}

@Preview
@Composable
fun LayoutStudyPreview(){
    ComPoseLearnTheme {
        LayoutStudy()
    }
}

