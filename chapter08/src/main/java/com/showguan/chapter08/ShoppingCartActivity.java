package com.showguan.chapter08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.adapter.CartAdapter;
import com.showguan.chapter08.database.ShoppingDBHelper;
import com.showguan.chapter08.entity.CartInfo;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private static String TAG = "Kennem";

    private ShoppingDBHelper mDBHelper;
    private Map<Integer, GoodsInfo> mGoodsMap;
    private List<CartInfo> mCartInfos;
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private TextView tv_count;
    private ListView lv_cart;
    private CartAdapter mCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        ll_empty = findViewById(R.id.ll_empty);
        lv_cart = findViewById(R.id.lv_cart);

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        tv_total_price = findViewById(R.id.tv_total_price);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
        findViewById(R.id.btn_go_to_market).setOnClickListener(this);


        mDBHelper = ShoppingDBHelper.getInstance(this);
        mGoodsMap = new HashMap<>();
        mCartInfos = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }


    // 展示购物车中的商品列表
    private void showCart() {
        // 查询购物车数据库中所有的商品记录
        mCartInfos = mDBHelper.queryAllCartInfo();
        if (mCartInfos.size() == 0) {
            lv_cart.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
            return;
        }
        lv_cart.setVisibility(View.VISIBLE);
        ll_empty.setVisibility(View.GONE);

        for (CartInfo info : mCartInfos) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo goods = mDBHelper.queryGoodsInfoByGoodsId(info.goodsId);
            mGoodsMap.put(info.goodsId, goods);
            info.goods = goods;
        }
        mCartAdapter = new CartAdapter(this, mCartInfos);
        lv_cart.setAdapter(mCartAdapter);
        // 给商品行添加点击事件。点击商品行跳到商品的详情页
        lv_cart.setOnItemClickListener(this);
        // 给商品行添加长按事件。长按商品行就删除该商品
        lv_cart.setOnItemLongClickListener(this);

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
    }


    private void deleteGoods(CartInfo cartInfo) {
        MyApplication.getInstance().goodsCount -= cartInfo.count;
        mDBHelper.deleteCartInfoByGoodsId(cartInfo.goodsId);
        CartInfo removed = null;
        for (CartInfo info : mCartInfos) {
            if(info.goodsId == cartInfo.goodsId){
                removed = info;
            }
        }
        mCartInfos.remove(removed);
        mCartAdapter.notifyDataSetChanged();
        showCount();
        ToastUtil.show(this, "已从购物车内删除" + mGoodsMap.get(cartInfo.goodsId).name);
        mGoodsMap.remove(cartInfo.goodsId);
        refreshTotalPrice();
    }

    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        if(MyApplication.getInstance().goodsCount == 0){
            ll_empty.setVisibility(View.VISIBLE);
            lv_cart.setVisibility(View.GONE);
            mCartAdapter.notifyDataSetChanged();

        }else{
            ll_empty.setVisibility(View.GONE);
            lv_cart.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTotalPrice(){
        double totalPrice = 0;
        for (CartInfo cartInfo : mCartInfos) {
            GoodsInfo goodsInfo = mGoodsMap.get(cartInfo.goodsId);
            totalPrice += goodsInfo.price * cartInfo.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back){
            finish();
        }else if (v.getId() == R.id.iv_cart){
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShoppingDetailActivity.class);
        intent.putExtra("goods_id", mCartInfos.get(position).goodsId);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CartInfo info = mCartInfos.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否从购物车删除" + info.goods.name + "?");
        builder.setPositiveButton("是", (dialog, which) -> {
            mCartInfos.remove(position);
            mCartAdapter.notifyDataSetChanged();
            deleteGoods(info);
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
        return true;
    }
}