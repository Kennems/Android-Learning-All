package com.showguan.chapter08;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.showguan.chapter08.adapter.ImagePagerAdapter;
import com.showguan.chapter08.entity.GoodsInfo;
import com.showguan.chapter08.util.ToastUtil;

import java.util.ArrayList;

public class ViewPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // 商品信息列表
    private ArrayList<GoodsInfo> mGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前活动的布局文件
        setContentView(R.layout.activity_view_page);

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
    }

    /**
     * 在翻页过程中触发
     * @param position 当前页面位置
     * @param positionOffset 页面偏移百分比
     * @param positionOffsetPixels 页面偏移像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 可以在这里实现页面滚动时的相关逻辑
    }

    /**
     * 页面选中时触发
     * @param position 选中页面的位置
     */
    @Override
    public void onPageSelected(int position) {
        // 显示当前选中页面的商品名称
        ToastUtil.show(this, "当前滑动的页面为 " + mGoodsList.get(position).name);
    }

    /**
     * 翻页状态改变时触发
     * @param state 页面状态
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // 可以在这里实现页面滚动状态变化时的相关逻辑
    }
}
