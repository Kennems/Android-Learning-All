package com.showguan.chapter08;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.showguan.chapter08.adapter.ImagePagerAdapter;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

import java.util.ArrayList;

public class PagerTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    // 商品信息列表
    private ArrayList<GoodsInfo> mGoodsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_tab);

        initPagerStrip();
        initViewPager();

    }

    // 初始化翻页标签栏
    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.GRAY);
    }

    private void initViewPager() {
        // 获取 ViewPager 控件
        ViewPager vp_content = findViewById(R.id.vp_content);

        // 获取默认的商品信息列表
        mGoodsList = GoodsInfo.getDefaultList();

        // 创建 ImagePagerAdapter 适配器
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mGoodsList);

        // 为 ViewPager 设置适配器
        vp_content.setAdapter(adapter);

        // 设置页面变更监听器
        vp_content.addOnPageChangeListener(this);
        vp_content.setCurrentItem(2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "当前滑动的页面为 " + mGoodsList.get(position).name);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}