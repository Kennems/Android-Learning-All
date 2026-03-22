package com.showguan.chapter08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.database.ShoppingDBHelper;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_price;
    private TextView tv_desc;
    private int mGoodsId;
    private ImageView iv_thumb;
    private ShoppingDBHelper mDbHelper;
    private TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        mDbHelper = ShoppingDBHelper.getInstance(this);

        iv_thumb = findViewById(R.id.iv_thumb);
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        tv_desc = findViewById(R.id.tv_desc);
        tv_count = findViewById(R.id.tv_count);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);


        showDetail();
    }

    private void showDetail() {
        mGoodsId = getIntent().getIntExtra("goods_id", 0);
        if(mGoodsId > 0){
            GoodsInfo goodsInfo = mDbHelper.queryGoodsInfoByGoodsId(mGoodsId);
            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
            tv_title.setText(goodsInfo.name);
            tv_price.setText(String.valueOf(goodsInfo.price));
            tv_desc.setText(goodsInfo.description);
            tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back){
            finish();
        } else if (v.getId() == R.id.btn_add_cart) {
            addCart(mGoodsId);
        } else if (v.getId() == R.id.iv_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

    }

    private void addCart(int mGoodsId) {
        int count = ++ MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        mDbHelper.insertCartInfo(mGoodsId);
        ToastUtil.show(this, "添加到购物车成功");
    }
}