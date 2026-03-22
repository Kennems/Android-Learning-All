package com.guan.jetpackcomposestate.todo.one

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guan.jetpackcomposestate.todo.TodoItem

class TodoViewModel : ViewModel() {
    private var _todoItems: MutableLiveData<List<TodoItem>> = MutableLiveData(listOf<TodoItem>())
    val todoItems: LiveData<List<TodoItem>> = _todoItems

    fun addItem(item: TodoItem) {
        _todoItems.value = _todoItems.value?.plus(item)
    }
    fun removeItem(item: TodoItem) {
        _todoItems.value = _todoItems.value?.minus(item)
    }
}