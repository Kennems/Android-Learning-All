package com.guan.jetpackcomposestate.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.guan.jetpackcomposestate.todo.four.TodoScreen
import com.guan.jetpackcomposestate.todo.four.TodoViewModel
import com.guan.jetpackcomposestate.ui.theme.JetpackComposeStateTheme

class TodoActivity : ComponentActivity() {

    companion object {
        private const val TAG = "TodoActivity"
    }

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeStateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoActivityScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    private fun TodoActivityScreen(
        modifier: Modifier = Modifier
    ) {
        TodoScreen(
            items = todoViewModel.todoItems,
            currentlyEditing = todoViewModel.currentEditItem,
            onAddItem = todoViewModel::addItem,
            onRemoveItem = todoViewModel::removeItem,
            onStartEdit = todoViewModel::onEditItemSelected,
            onEditItemChange = todoViewModel::onEditItemChange,
            onEditDone = todoViewModel::onEditDone,
            modifier = modifier
        )
    }
}

