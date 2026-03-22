package com.guan.jetpackcomposecompositionlocal

import junit.framework.TestCase.assertEquals
import org.junit.Test

// 显式传参
class ExplicitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private fun Layout() {
        println("layout")
        val color: String = "red"
        Text(color)
        Grid(color)
        Grid(color)
        Text(color)
    }

    private fun Grid(color: String) {
        println("other components in grid")
        Text(color)
    }

    private fun Text(color: String) {
        println("Text")
        println(color)
    }

    @Test
    fun test_explicit() {
        Layout()
    }
}