package com.showguan.chapter08.entity;

//购物车信息
public class CartInfo {
    public int id;
    // 商品编号
    public int goodsId;
    // 商品数量
    public int count;

    public GoodsInfo goods;

    public CartInfo(){}

    public CartInfo(int id, int goodsId, int count, GoodsInfo goods) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", goods=" + goods +
                '}';
    }
}
