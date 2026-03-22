package com.show.carhome.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.show.carhome.model.CarBrandItemModel
import dagger.Module
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun fetchCarBrandList(): Flow<PagingData<CarBrandItemModel>>
}