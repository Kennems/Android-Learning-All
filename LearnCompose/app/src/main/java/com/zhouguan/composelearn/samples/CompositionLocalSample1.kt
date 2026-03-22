package com.zhouguan.composelearn.samples

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CompositionSample1(){
    MaterialTheme{
        CustomTextLabel(labelText = "Some ")
    }
}

@Composable
fun CustomTextLabel(labelText: String){
    Text(
        text = labelText,
        color = MaterialTheme.colorScheme.primary
    )
}