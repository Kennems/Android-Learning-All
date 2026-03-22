package com.showguan.chapter08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.showguan.chapter08.adapter.LaunchImproveAdapter;
import com.showguan.chapter08.adapter.LaunchPagerAdapter;

public class LaunchImproveActivity extends AppCompatActivity {

    // 定义一个数组，用于存储启动界面的背景图片资源ID
    private static int[] launchImageArray = {
            R.drawable.guide_bg1,
            R.drawable.guide_bg2,
            R.drawable.guide_bg3,
            R.drawable.guide_bg4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_improve);


        // 查找布局文件中的ViewPager控件
        ViewPager vp_launch = findViewById(R.id.vp_launch);
        // 创建适配器对象，将图片数组传递进去
        LaunchImproveAdapter adapter = new LaunchImproveAdapter(getSupportFragmentManager(), launchImageArray);
        // 为ViewPager设置适配器
        vp_launch.setAdapter(adapter);
    }
}