package com.show.carhome.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.show.carhome.db.AppDatabase
import com.show.carhome.entity.CarBrandEntity
import com.show.carhome.ext.isConnectedNetWork
import com.show.carhome.init.AppHelper

@OptIn(ExperimentalPagingApi::class)
class CarBrandRemoteMediator(
    private val api: CarBrandService, // 依赖注入的API服务，用于请求网络数据
    private val database: AppDatabase // 依赖注入的数据库实例，用于数据存储
) : RemoteMediator<Int, CarBrandEntity>() {

    // 负责加载分页数据的核心方法
    override suspend fun load(
        loadType: LoadType, // 加载类型，可能是REFRESH、PREPEND或APPEND
        state: PagingState<Int, CarBrandEntity> // 用于获取当前分页状态
    ): MediatorResult {
        try {
            Log.d("Kennem", "loadType = ${loadType}")

            // 确定当前请求的页码
            val pageKey = when (loadType) {
                // 首次加载或手动刷新时，不需要页码，设为null
                LoadType.REFRESH -> null
                // 向前加载数据时，直接返回，不继续请求，标记为分页结束
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                // 向后加载更多数据时，获取最后一项的数据页码作为新的页码
                LoadType.APPEND -> {
                    val lastItem: CarBrandEntity = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page
                }
            }

            // 无网络，加载本地数据
            if (!AppHelper.mContext.isConnectedNetWork()){
                MediatorResult.Success(endOfPaginationReached = true)
            }

            // 请求网络分页数据，pageKey为null时默认请求第一页数据
            val page = pageKey ?: 0
            val result = api.fetchCarBrandList(
                page * state.config.pageSize, // 根据页码和页面大小计算请求的偏移量
                state.config.pageSize // 页面大小
            )

            // 将网络数据映射为数据库实体
            val item = result.map {
                CarBrandEntity(
                    id = it.id,
                    name = it.name,
                    icon = it.icon,
                    page = page + 1 // 将页码加一，标记为下一次请求的页码
                )
            }

            // 判断是否到达分页末端
            val endOfPaginationReached = result.isEmpty()

            // 获取DAO操作对象
            val carBrandDao = database.carBrandDao()

            // 使用事务插入数据，保证数据一致性
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // 如果是刷新操作，清空已有的数据
                    carBrandDao.clearCarBrand()
                }
                // 插入新数据
                carBrandDao.insertCarBrand(item)
            }

            // 返回加载结果，标记是否到达分页末端
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            // 捕获异常，返回错误结果
            return MediatorResult.Error(e)
        }
    }
}
