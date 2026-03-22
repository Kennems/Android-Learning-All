package com.guan.jetpackcomposecompositionlocal

import org.junit.Test

// 隐式传参
class ImplicitTest {
    var color: String = "red"

    private fun Layout(){
        println("layout")
        Text(color)
        provider("blue"){
            Grid(color)
        }
        Grid(color)
        Grid(color)
        Text(color)
    }

    private fun Grid(color: String){
        println("other components in grid")
        Text(color)
    }

    private fun Text(color: String) {
        println("Text")
        println(color)
    }

    private fun provider(value: String, content: () -> Unit){
        println("provider")
        val tmpColor = color

        color = value
        content()

        color = tmpColor
    }

    @Test
    fun test_implicit(){
        Layout()
    }
}