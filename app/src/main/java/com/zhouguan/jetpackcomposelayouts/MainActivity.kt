package com.zhouguan.jetpackcomposelayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhouguan.jetpackcomposelayouts.ui.theme.JetpackComposeLayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        WindowCompat.setDecorFitsSystemWindows(window, false) // 等价于 enableEdgeToEdge()
        setContent {
            JetpackComposeLayoutsTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //                JetPackComposeLayouts()
                    //                layoutStudy()
                    //                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //                    Greeting(
                    //                        name = "Android",
                    //                        modifier = Modifier.padding(innerPadding)
                    //                    )
                    //                }
                    //                SimpleColumn()
                    //                SimpleList()
                    //                LazyList()
                    //                ScrollingList()
                    //                MyOwnColumnSample()
                    //                StaggeredGrid()
                    //                StaggeredGridBodyContent()
                    //                ConstraintsLayoutContent()
                    //                ConstraintsLayoutContent2()
                    //                LargeLayoutContent()

                    DecoupledConstraintLayout2(modifier = Modifier.padding(innerPadding))

//                    TwoTexts(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeLayoutsTheme {
        Greeting("Android")
    }
}