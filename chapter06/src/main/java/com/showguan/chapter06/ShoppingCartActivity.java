package com.showguan.chapter06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter06.database.ShoppingDBHelper;
import com.showguan.chapter06.enity.CartInfo;
import com.showguan.chapter06.enity.CartItemInfo;
import com.showguan.chapter06.enity.GoodsInfo;
import com.showguan.chapter06.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "Kennem";

    private LinearLayout ll_content;
    private ShoppingDBHelper mDBHelper;
    private Map<Integer, GoodsInfo> mGoodsMap;
    private List<CartInfo> mCartInfos;
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private TextView tv_count;
    private LinearLayout ll_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        ll_content = findViewById(R.id.ll_content);
        ll_empty = findViewById(R.id.ll_empty);
        ll_cart = findViewById(R.id.ll_cart);

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

//    private void showCart() {
//        ll_content.removeAllViews();
//        mCartInfos = mDBHelper.queryAllCartInfo();
//        if(mCartInfos.size() == 0){
//            ll_empty.setVisibility(View.VISIBLE);
//            ll_cart.setVisibility(View.GONE);
//            return ;
//        }
//        ll_empty.setVisibility(View.GONE);
//        ll_cart.setVisibility(View.VISIBLE);
//        Log.d(TAG, mCartInfos.toString());
//
//        for (CartInfo cartInfo : mCartInfos) {
//            GoodsInfo goodsInfo = mDBHelper.queryGoodsInfoByGoodsId(cartInfo.goodsId);
//            Log.d(TAG, goodsInfo.toString());
//            mGoodsMap.put(cartInfo.goodsId, goodsInfo);
//            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
//            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
//            TextView tv_phone_name = view.findViewById(R.id.tv_phone_name);
//            TextView tv_phone_desc = view.findViewById(R.id.tv_phone_desc);
//            TextView tv_phone_count = view.findViewById(R.id.tv_phone_count);
//            TextView tv_phone_price = view.findViewById(R.id.tv_phone_price);
//            TextView tv_total_price = view.findViewById(R.id.tv_total_price);
//
//            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
//            tv_phone_name.setText(goodsInfo.name);
//            tv_phone_desc.setText(goodsInfo.description);
//            tv_phone_count.setText(String.valueOf(cartInfo.count));
//            tv_phone_price.setText(String.valueOf(goodsInfo.price));
//            // 总价
//            tv_total_price.setText(String.valueOf(cartInfo.count * goodsInfo.price));
//
//            view.setOnLongClickListener(v->{
//                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
//                builder.setMessage("是否要删除商品" + goodsInfo.name + "?");
//                builder.setPositiveButton("是", ((dialog, which) -> {
//                    ll_content.removeView(v);
//                    deleteGoods(cartInfo);
//                }));
//                builder.setNegativeButton("否", null);
//                builder.create().show();
//                return true;
//            });
//
//            view.setOnClickListener(v->{
//                Intent intent = new Intent(this, ShoppingDetailActivity.class);
//                intent.putExtra("goods_id", goodsInfo.id);
//                startActivity(intent);
//            });
//            ll_content.addView(view);
//        }
//        refreshTotalPrice();
//    }

    private void showCart() {
        ll_content.removeAllViews();
        List<CartItemInfo> cartItemInfos = mDBHelper.queryCartAndGoodsInfo();

        if (cartItemInfos.size() == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_cart.setVisibility(View.GONE);
            return;
        }
        Log.d(TAG, cartItemInfos.toString());
        ll_empty.setVisibility(View.GONE);
        ll_cart.setVisibility(View.VISIBLE);
        for (CartItemInfo cartItemInfo : cartItemInfos) {
            CartInfo cartInfo = cartItemInfo.cartInfo;
            GoodsInfo goodsInfo = cartItemInfo.goodsInfo;

            mGoodsMap.put(cartInfo.goodsId, goodsInfo);
            mCartInfos.add(cartInfo);

            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_phone_name = view.findViewById(R.id.tv_phone_name);
            TextView tv_phone_desc = view.findViewById(R.id.tv_phone_desc);
            TextView tv_phone_count = view.findViewById(R.id.tv_phone_count);
            TextView tv_phone_price = view.findViewById(R.id.tv_phone_price);
            TextView tv_total_price = view.findViewById(R.id.tv_total_price);

            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
            tv_phone_name.setText(goodsInfo.name);
            tv_phone_desc.setText(goodsInfo.description);
            tv_phone_count.setText(String.valueOf(cartInfo.count));
            tv_phone_price.setText(String.valueOf(goodsInfo.price));
            // 总价
            tv_total_price.setText(String.valueOf(cartInfo.count * goodsInfo.price));

            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setMessage("是否要删除商品" + goodsInfo.name + "?");
                builder.setPositiveButton("是", (dialog, which) -> {
                    ll_content.removeView(v);
                    deleteGoods(cartInfo);
                });
                builder.setNegativeButton("否", null);
                builder.create().show();
                return true;
            });

            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id", goodsInfo.id);
                startActivity(intent);
            });
            ll_content.addView(view);
        }
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
        showCount();
        ToastUtil.show(this, "已从购物车内删除" + mGoodsMap.get(cartInfo.goodsId).name);
        mGoodsMap.remove(cartInfo.goodsId);
        refreshTotalPrice();
    }

    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        if(MyApplication.getInstance().goodsCount == 0){
            ll_empty.setVisibility(View.VISIBLE);
            ll_cart.setVisibility(View.GONE);
        }else{
            ll_empty.setVisibility(View.GONE);
            ll_cart.setVisibility(View.VISIBLE);
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
        if(v.getId() == R.id.iv_back) {
            finish();
        }else if(v.getId() == R.id.btn_clear){
            mDBHelper.deleteAllCartInfo();
            MyApplication.getInstance().goodsCount = 0;
            showCount();
            ToastUtil.show(this, "成功清空购物车");
        } else if (v.getId() == R.id.btn_settle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("结算商品");
            builder.setMessage("客观，支付功能尚未开通，请下次再来！");
            builder.setPositiveButton("我知道了", null);
            builder.create().show();
        } else if(v.getId() == R.id.btn_go_to_market){
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}