package com.show.carhome.remote

import com.show.carhome.model.CarBrandItemModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CarBrandService {
    @GET("carBrand.do")
    suspend fun fetchCarBrandList(
        @Query("since") since: Int,
        @Query("pagesize") pagesize: Int
    ): List<CarBrandItemModel>
}