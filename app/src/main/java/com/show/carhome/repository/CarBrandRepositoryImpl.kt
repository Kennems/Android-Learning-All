package com.show.carhome.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.show.carhome.db.AppDatabase
import com.show.carhome.entity.CarBrandEntity
import com.show.carhome.mapper.Mapper
import com.show.carhome.model.CarBrandItemModel
import com.show.carhome.remote.CarBrandRemoteMediator
import com.show.carhome.remote.CarBrandService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * CarBrandRepositoryImpl 是 Repository 接口的实现类。
 * 它负责从远程 API 和本地数据库中获取汽车品牌数据，并将其转换为适合 UI 显示的格式。
 *
 * @param api 远程 API 服务接口，用于从网络获取汽车品牌数据
 * @param database 本地数据库实例，用于从数据库中读取和写入数据
 * @param mapper2ItemModel 数据转换器，将 CarBrandEntity 转换为 CarBrandItemModel
 */
class CarBrandRepositoryImpl(
    private val api: CarBrandService, // 用于访问远程 API 的服务
    private val database: AppDatabase, // 本地数据库实例
    private val mapper2ItemModel: Mapper<CarBrandEntity, CarBrandItemModel> // 数据转换器
) : Repository {

    /**
     * 从数据源中获取汽车品牌列表。
     * 数据来自网络并缓存到数据库中，之后从数据库中读取数据并进行转换。
     *
     * @return 返回一个 Flow<PagingData<CarBrandItemModel>> 对象，用于观察分页数据的变化
     */
    @OptIn(ExperimentalPagingApi::class) // 表明使用了 ExperimentalPagingApi API
    override fun fetchCarBrandList(): Flow<PagingData<CarBrandItemModel>> {
        // 创建一个 Pager 对象来处理分页数据
        return Pager(
            config = PagingConfig(
                pageSize = 8, // 每页的数据条数
                prefetchDistance = 1, // 提前加载的距离
                initialLoadSize = 16 // 初始加载的数据条数
            ),
            remoteMediator = CarBrandRemoteMediator(api, database) // 用于请求网络数据并缓存到数据库中
        ) {
            // 从数据库中获取数据
            database.carBrandDao().getCarBrand() // 访问数据库中的 DAO 获取数据
        }.flow // 获取分页数据流
            .flowOn(Dispatchers.IO) // 在 IO 线程中执行流操作
            .map { pagingData ->
                // 将分页数据中的每项数据进行转换
                pagingData.map { mapper2ItemModel.map(it) } // 使用转换器将数据转换为 UI 需要的格式
            }
    }
}
