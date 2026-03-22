package com.showguan.chapter08; // 包名

import android.os.Bundle; // 导入Bundle类
import android.view.View; // 导入View类
import android.widget.AdapterView; // 导入AdapterView类
import android.widget.Spinner; // 导入Spinner类

import androidx.appcompat.app.AppCompatActivity; // 导入AppCompatActivity类

import com.showguan.chapter08.adapter.PlanetBaseAdapter; // 导入自定义的PlanetBaseAdapter类
import com.showguan.chapter08.entity.Planet; // 导入自定义的Planet类
import com.showguan.chapter08.util.ToastUtil; // 导入自定义的ToastUtil类

import java.util.List; // 导入List接口

public class BaseAdapterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // 定义活动类，实现选项选择监听器

    private static final String[] starArray = new String[]{ // 定义星球名称数组
            "水星", "金星", "地球", "火星", "木星", "土星", "天王星", "海王星"
    };
    private List<Planet> planetList; // 存储Planet对象的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 活动创建时调用
        super.onCreate(savedInstanceState); // 调用父类的onCreate方法
        setContentView(R.layout.activity_base_adapter); // 设置活动的布局文件
        Spinner sp_planet = findViewById(R.id.sp_planet); // 获取Spinner控件
        planetList = Planet.getDefaultList(); // 获取默认的星球列表
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planetList); // 创建适配器
        sp_planet.setAdapter(adapter); // 为Spinner设置适配器
        sp_planet.setSelection(0); // 默认选中第0项
        sp_planet.setOnItemSelectedListener(this); // 设置选项选中监听器
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // 当选择一个选项时调用
        ToastUtil.show(this, "您选择的是" + planetList.get(position).name); // 显示选择的星球名称
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // 当没有选项被选择时调用

    }
}
