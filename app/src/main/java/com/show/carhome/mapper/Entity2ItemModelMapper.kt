package com.show.carhome.mapper

import com.show.carhome.entity.CarBrandEntity
import com.show.carhome.model.CarBrandItemModel

/**
 * Entity2ItemModelMapper 是一个实现了 Mapper 接口的类，用于将 CarBrandEntity 对象转换为 CarBrandItemModel 对象。
 * @param I 输入类型，CarBrandEntity
 * @param O 输出类型，CarBrandItemModel
 */
class Entity2ItemModelMapper : Mapper<CarBrandEntity, CarBrandItemModel> {

    /**
     * 将 CarBrandEntity 对象转换为 CarBrandItemModel 对象。
     * @param input 要转换的 CarBrandEntity 实例
     * @return 转换后的 CarBrandItemModel 实例
     */
    override fun map(input: CarBrandEntity): CarBrandItemModel =
        // 创建并返回一个 CarBrandItemModel 实例，将 CarBrandEntity 的属性值赋给 CarBrandItemModel 的相应属性
        CarBrandItemModel(
            id = input.id,      // 从 CarBrandEntity 中提取 id
            name = input.name,  // 从 CarBrandEntity 中提取 name
            icon = input.icon   // 从 CarBrandEntity 中提取 icon
        )
}
