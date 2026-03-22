package com.guan.coroutinecompare

import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() = runBlocking<Unit> {
    // 启动一个协程作为父协程
    val job = launch {
        // 在父协程中启动一个子协程
        val child = launch {
            try {
                // 子协程尝试延迟执行，模拟长时间运行的任务
//                delay(Long.MAX_VALUE)
                for(i in 1..Long.MAX_VALUE){
                    val x = i * 10
                }
            } finally {
                // 当子协程被取消时，执行此块
                println("Child is cancelled")
            }
        }
        // 让父协程暂时让出线程，给其他协程执行的机会
//        println("Cancelling child")
//        // 取消子协程，并等待其执行完 finally 块
//        child.cancelAndJoin()
//        // 再次让出线程
//        yield()
//        println("Parent is not cancelled")
    }
    // 等待父协程完成执行
    job.join()
}

