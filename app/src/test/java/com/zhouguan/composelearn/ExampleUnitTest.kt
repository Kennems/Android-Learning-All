package com.zhouguan.composelearn

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Composable
    fun FancyBox(children: @Composable () -> Unit) {
        Box() {
            children()
        }
    }

    @Composable
    fun Story() {
        FancyBox() {
            Text("Hello", style = MaterialTheme.typography.bodySmall)
        }
    }

    @Composable
    fun FancyStory() {
        FancyBox() {
            Story()
        }
    }

    @Composable
    fun EditForm() {
        TextField(value = "", onValueChange = {}, label = { Text("Input your name") })
    }

    @Composable
    fun FancyEditForm(){
        FancyBox {
            EditForm()
        }
    }

    @Composable
    fun MyApp(){
        FancyStory()
        FancyEditForm()
    }
}