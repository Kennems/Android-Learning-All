package com.showguan.chapter06.enity;


import android.util.Log;

import com.showguan.chapter06.R;

import java.util.ArrayList;

public class GoodsInfo {

    private static String TAG = "Kennem";

    public int id;
    // 名称
    public String name;
    // 描述
    public String description;
    // 价格
    public float price;
    // 大图的保存路径
    public String picPath;
    // 大图的资源编号
    public int pic;

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "Apple iPhone 15 Pro", "华为pura70pro", "小米14Pro", "OPPO Find X7", "vivo X100s", "荣耀Magic6 Pro"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "Apple iPhone 15 Pro (A3104) 256GB 白色钛金属 支持移动联通电信5G 双卡双待手机",
            "华为pura70pro 华为手机新品 华为p70 华为p70pro 手机上市 雪域白 12GB+512GB 官方标配",
            "小米14Pro 徕卡可变光圈镜头 光影猎人900 澎湃OS 16+512 黑色 5G AI手机 小米汽车互联",
            "OPPO Find X7 12GB+256GB 海阔天空 天玑 9300 超光影三主摄 专业哈苏人像 长续航 5.5G 拍照 AI手机",
            "vivo X100s 12GB+256GB 白月光 蓝晶×天玑9300+ 蔡司超级长焦 7.8mm超薄直屏 5G 拍照 手机",
            "荣耀Magic6 Pro 荣耀鸿燕通信 单反级荣耀鹰眼相机 荣耀巨犀玻璃 16GB+512GB 海湖青 5G AI手机"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {6299, 4999, 3999, 2999, 2998, 2399};
    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
            R.drawable.oppo, R.drawable.vivo, R.drawable.rongyao
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescArray[i];
            info.price = mPriceArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
            Log.d(TAG, info.toString());
        }
        return goodsList;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", picPath='" + picPath + '\'' +
                ", pic=" + pic +
                '}';
    }
}
