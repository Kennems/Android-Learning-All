package com.showguan.chapter06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter06.database.ShoppingDBHelper;
import com.showguan.chapter06.enity.CartInfo;
import com.showguan.chapter06.enity.GoodsInfo;
import com.showguan.chapter06.util.ToastUtil;

import org.w3c.dom.Text;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "Kennem";

    private ShoppingDBHelper mDBHelper;
    private TextView tv_title;
    private GridLayout gl_channel;
    private TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商城");

        tv_count = findViewById(R.id.tv_count);
        gl_channel = findViewById(R.id.gl_channel);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        mDBHelper = ShoppingDBHelper.getInstance(this);
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
        showGoods();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCartTotalInfo();
    }

    private void showCartTotalInfo() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    private void showGoods() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);

        List<GoodsInfo> list = mDBHelper.queryAllGoods();

        gl_channel.removeAllViews();

        for (GoodsInfo info : list) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            Button btn_add = view.findViewById(R.id.btn_add);
            btn_add.setOnClickListener(v -> {
                addToCart(info.id, info.name);
            });
            Log.d(TAG, info.picPath);
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf(info.price));
            gl_channel.addView(view, layoutParams);

            iv_thumb.setOnClickListener(v->{
                Intent intent = new Intent(this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id", info.id);
                startActivity(intent);
            });

        }
    }

    private void addToCart(int goodsId, String name) {
        mDBHelper.insertCartInfo(goodsId);
        int count = ++ MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        ToastUtil.show(this, "已添加一部 "+ name + "到购物车成功！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
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
}