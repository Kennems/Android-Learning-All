package com.showguan.chapter08.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.fragment.DynamicFragment;

import java.util.List;

public class MobilePagerAdapter extends FragmentPagerAdapter {
    // 商品信息列表
    private final List<GoodsInfo> mGoodsList;

    /**
     * 构造函数，初始化适配器
     * @param fm FragmentManager 用于管理 Fragment 的事务
     * @param mGoodsList 商品信息列表
     */
    public MobilePagerAdapter(@NonNull FragmentManager fm, List<GoodsInfo> mGoodsList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mGoodsList = mGoodsList;
    }

    /**
     * 获取指定位置的 Fragment
     * @param position 位置索引
     * @return 对应位置的 Fragment 实例
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 从列表中获取商品信息
        GoodsInfo info = mGoodsList.get(position);
        // 创建并返回新的 DynamicFragment 实例
        return DynamicFragment.newInstance(position, info.pic, info.description);
    }

    /**
     * 获取列表中的项目数量
     * @return 商品信息列表的大小
     */
    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    /**
     * 获取页面标题
     * @param position 位置索引
     * @return 对应位置的页面标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // 返回商品名称作为页面标题
        return mGoodsList.get(position).name;
    }
}
