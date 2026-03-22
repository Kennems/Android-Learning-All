package com.showguan.chapter08;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.adapter.GoodsGridAdapter;
import com.showguan.chapter08.database.ShoppingDBHelper;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

import java.util.List;

// ShoppingChannelActivity 类实现了 GoodsGridAdapter.AddCartListener 和 View.OnClickListener 接口
public class ShoppingChannelActivity extends AppCompatActivity implements GoodsGridAdapter.AddCartListener, View.OnClickListener {
    private static String TAG = "Kennem"; // 日志标签

    private ShoppingDBHelper mDBHelper; // 数据库助手对象
    private TextView tv_title; // 显示标题的 TextView
    private GridView gv_channel; // 显示商品的 GridView
    private TextView tv_count; // 显示购物车商品数量的 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置活动的布局文件
        setContentView(R.layout.activity_shopping_channel);

        // 初始化控件
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商城");

        tv_count = findViewById(R.id.tv_count);
        gv_channel = findViewById(R.id.gv_channel);

        // 设置点击事件监听器
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        // 初始化数据库助手并打开读写链接
        mDBHelper = ShoppingDBHelper.getInstance(this);
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();

        // 显示商品信息
        showGoods();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在活动恢复时，显示购物车的总信息
        showCartTotalInfo();
    }

    // 显示购物车总信息的方法
    private void showCartTotalInfo() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    // 显示商品信息的方法
    private void showGoods() {
        List<GoodsInfo> list = mDBHelper.queryAllGoods();
        GoodsGridAdapter adapter = new GoodsGridAdapter(this, list, this);
        gv_channel.setAdapter(adapter);
    }

    // 添加商品到购物车的方法
    @Override
    public void addToCart(int goodsId, String name) {
        mDBHelper.insertCartInfo(goodsId);
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        ToastUtil.show(this, "已添加一部 " + name + "到购物车成功！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在活动销毁时，关闭数据库链接
        mDBHelper.closeLink();
    }

    // 处理点击事件的方法
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            // 点击返回按钮时，结束当前活动
            finish();
        } else if (v.getId() == R.id.iv_cart) {
            // 点击购物车按钮时，启动购物车活动
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
