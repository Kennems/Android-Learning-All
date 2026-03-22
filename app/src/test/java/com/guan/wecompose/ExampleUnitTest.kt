package com.guan.wecompose

import org.junit.Assert.assertEquals
import org.junit.Test

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
}

fun call(f: () -> Unit) {
    f.invoke()
}

fun <T, R> safeCall(f: ((T) -> R)?, arg: T) {
    f?.invoke(arg)
}


fun main() {
    val f: ((Int) -> Int) = { x -> x + 1 }

    println(f)
    println(f(110))

    val pt = { println("Hello") }

    call { pt }
    call { pt() }
}