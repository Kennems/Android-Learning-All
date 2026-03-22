package com.showguan.chapter08.entity;

public class CartItemInfo {
    public CartInfo cartInfo;
    public GoodsInfo goodsInfo;

    public CartItemInfo(CartInfo cartInfo, GoodsInfo goodsInfo) {
        this.cartInfo = cartInfo;
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String toString() {
        return "CartItemInfo{" +
                "cartInfo=" + cartInfo +
                ", goodsInfo=" + goodsInfo +
                '}';
    }
}
