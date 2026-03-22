package com.guan.coroutinecompare.samples

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds


suspend fun main() {
    fun runblk(block: () -> Unit) {
        for (i in 0..100) {
            block(i)
            log("\n")
        }
    }

    runblk {
        block(Math.E.toInt())
    }
}

fun block(times: Int) = runBlocking {
    val deferred: Deferred<Int> = async(Dispatchers.Default) {
        load(times)
    }
    launch {
        log("waiting ...")
        for (i in 0 until 100) {
            log("Doing somethings $i 😎🔥🚀")
        }
    }
    log(deferred.await())
}

suspend fun load(times: Int): Int {
    log("loading ...")
    delay(2.seconds)
    log("loaded !")

    return times
}