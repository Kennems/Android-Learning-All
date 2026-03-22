package com.zhouguan.jetpackcomposelayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun SimpleColumn() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        repeat(100) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

// list view
@Composable
fun SimpleList() {
//    val scrollState = ScrollState(initial = 0)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .verticalScroll(scrollState)
    ) {
        repeat(100) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

// recycler view
@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        state = scrollState
    ) {
        items(101) {
            Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun ScrollingList() {
    val scrollState = rememberLazyListState()
    val listSize = 100
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())

    ) {
        Row(

        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch{
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Text(text = "Scroll to the top")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch{
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }
            ) {
                Text(text = "Scroll to the end")
            }
        }

        LazyColumn(
            state = scrollState
        ) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = "https://global.bing.com/th?id=OHR.JaipurFort_ZH-CN3891828158_1920x1200.jpg"),
            contentDescription = "Image $index",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}