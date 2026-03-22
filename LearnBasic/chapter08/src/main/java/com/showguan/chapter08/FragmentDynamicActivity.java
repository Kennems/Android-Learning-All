package com.showguan.chapter08;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.showguan.chapter08.adapter.MobilePagerAdapter;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

import java.util.ArrayList;

public class FragmentDynamicActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static String TAG = "Kennem";

    // 商品信息列表
    private ArrayList<GoodsInfo> mGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_dynamic);
        initPagerStrip();
        initViewPager();
    }

    // 初始化翻页标签栏
    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.GRAY);
        pts_tab.setDrawFullUnderline(false);
        pts_tab.setTabIndicatorColor(Color.BLUE);
    }

    private void initViewPager() {
        // 获取 ViewPager 控件
        ViewPager vp_content = findViewById(R.id.vp_content);
        // 获取默认的商品信息列表
        mGoodsList = GoodsInfo.getDefaultList();
        Log.d(TAG, mGoodsList.toString());
        // 创建 MobilePagerAdapter 适配器
        MobilePagerAdapter adapter = new MobilePagerAdapter(getSupportFragmentManager(), mGoodsList);
        // 为 ViewPager 设置适配器
        vp_content.setAdapter(adapter);
        // 设置页面变更监听器
        vp_content.addOnPageChangeListener(this);
        vp_content.setCurrentItem(2); // 设置当前显示的页
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 页面滑动时触发
    }

    @Override
    public void onPageSelected(int position) {
        // 页面选中时触发
        ToastUtil.show(this, "当前滑动的页面为 " + mGoodsList.get(position).name);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 页面滑动状态改变时触发
    }
}
