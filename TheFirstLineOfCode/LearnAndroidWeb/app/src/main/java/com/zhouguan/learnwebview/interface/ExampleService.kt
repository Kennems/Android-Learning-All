package com.zhouguan.learnwebview.`interface`

import com.zhouguan.learnwebview.bean.Data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExampleService {
    @GET("get_data.json")
    fun getData(): Call<Data>

    // GET http://example.com/<page>/get_data.json
    @GET("{page}/get_data.json")
    fun getData(@Path("page") page: Int): Call<Data>

    // GET http://example.com/get_data.json?u=<user>&t=<token>
    @GET("get_data.json")
    fun getData(@Query("u") user: String, @Query("t") token: String): Call<Data>

    // DELETE http://example.com/data/<id>
    @DELETE("data/{id}")
    fun deleteData(@Path("id") id: String): Call<ResponseBody>

    /**
     * POST http://example.com/data/create
     * {"id": 1, "content": "The description for this data."}
     */
    @POST("data/create")
    fun createData(@Body data: Data): Call<ResponseBody>

    /**
     * GET http://example.com/get_data.json
     * User-Agent: okhttp
     * Cache-Control: max-age=0
     */
    @Headers("User-Agent: okhttp", "Cache-Control: max-age=0")
    @GET("get_data.json")
    fun getData2(): Call<Data>

    // 动态指定Headers
    @GET("get_data.json")
    fun getData3(
        @Header("User-Agent") userAgent: String,
        @Header("Cache-Control") cacheControl: String
    ): Call<Data>

}