package com.guan.coroutinecompare.utils

import retrofit2.Response


fun<T> Boolean.select(choice1: T, choice2: T) =
    if (this) choice1 else choice2

fun <T> Response<List<T>>.bodyList(): List<T> {
    return body() ?: emptyList()
}