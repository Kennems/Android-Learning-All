package com.guan.hiltlearn.ioc

fun main() {
    val engine = Engine()
    val car = Car(engine)
    car.start()
}