package com.show.carhome.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.show.carhome.model.CarBrandItemModel
import com.show.carhome.repository.CarBrandRepositoryImpl
import com.show.carhome.repository.Repository
import javax.inject.Inject

/**
 * MainViewModel 是一个用于处理主界面逻辑的 ViewModel。
 * 它通过注入的 Repository 获取汽车品牌的数据，并将其转换为 LiveData<PagingData<CarBrandItemModel>>。
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    // 通过构造函数注入的 Repository 实例，用于从数据源中获取汽车品牌的数据。
    carBrandRepository: Repository
) : ViewModel() {

    /**
     * data 是一个 LiveData 对象，持有 PagingData<CarBrandItemModel>。
     * 它通过 carBrandRepository 从数据源中获取汽车品牌的列表，并将其缓存到 viewModelScope 中。
     */
    val data: LiveData<PagingData<CarBrandItemModel>> =
        carBrandRepository
            .fetchCarBrandList() // 从 Repository 获取 PagingData<CarBrandItemModel>
            .cachedIn(viewModelScope) // 将数据缓存到 viewModelScope，避免 ViewModel 被销毁时数据丢失
            .asLiveData() // 将 Flow<PagingData<CarBrandItemModel>> 转换为 LiveData<PagingData<CarBrandItemModel>>
}