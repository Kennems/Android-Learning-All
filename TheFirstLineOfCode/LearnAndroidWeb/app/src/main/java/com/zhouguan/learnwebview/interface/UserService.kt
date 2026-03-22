package com.zhouguan.learnwebview.`interface`

import com.zhouguan.learnwebview.bean.App
import com.zhouguan.learnwebview.bean.Person
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("users")
    fun getUserData(): Call<List<Person>>
}