package com.show.carhome.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.show.carhome.entity.CarBrandEntity

@Dao
interface CarBrandDao {
    @Query("DELETE FROM CarBrandEntity")
    fun clearCarBrand()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarBrand(carBrandList: List<CarBrandEntity>)

    @Query("SELECT * FROM CarBrandEntity")
    fun getCarBrand(): PagingSource<Int, CarBrandEntity> // 移除 suspend
}