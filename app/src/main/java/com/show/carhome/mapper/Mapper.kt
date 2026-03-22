package com.show.carhome.mapper

/**
 * `Mapper` 接口用于定义一个通用的映射功能，将输入类型转换为输出类型。
 *
 * @param I 输入类型
 * @param O 输出类型
 */
interface Mapper<I, O> {

    /**
     * 将输入对象转换为输出对象的映射函数。
     *
     * @param input 要转换的输入对象
     * @return 转换后的输出对象
     */
    fun map(input: I): O
}
