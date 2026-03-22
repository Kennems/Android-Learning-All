package com.guan.jetpackcomposestate.util

import com.zhouguan.composelearn.todo.TodoIcon
import com.zhouguan.composelearn.todo.TodoItem

fun generateRandomToolItem(): TodoItem {
    val message = listOf(
        "Learn Compose",
        "Master Kotlin basics",
        "Explore Jetpack libraries",
        "Build your first Android app",
        "Understand State and LiveData",
        "Practice dependency injection",
        "Try out Coroutines",
        "Design beautiful UIs",
        "Read about Material Design",
        "Debug and profile your app",
        "Publish your app to Play Store",
        "Write unit tests",
        "Join Android communities",
        "Contribute to open source"
    )

    val icon = TodoIcon.entries.toTypedArray().random()

    return TodoItem(
        task = message.random(),
        icon = icon
    )
}