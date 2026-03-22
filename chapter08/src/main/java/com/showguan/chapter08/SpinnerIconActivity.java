package com.showguan.chapter08; // 包名

import android.os.Bundle; // 导入Bundle类
import android.view.View; // 导入View类
import android.widget.AdapterView; // 导入AdapterView类
import android.widget.SimpleAdapter; // 导入SimpleAdapter类
import android.widget.Spinner; // 导入Spinner类

import androidx.appcompat.app.AppCompatActivity; // 导入AppCompatActivity类

import com.showguan.chapter08.util.ToastUtil; // 导入自定义的ToastUtil类

import java.util.ArrayList; // 导入ArrayList类
import java.util.HashMap; // 导入HashMap类
import java.util.List; // 导入List接口
import java.util.Map; // 导入Map接口

public class SpinnerIconActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // 定义一个整型数组，存储星球图标的资源ID
    private static final int[] iconArray = {
            R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing,
    };

    // 定义一个字符串数组，存储星球的名称
    private static final String[] starArray = new String[]{
            "水星", "金星", "地球", "火星", "木星", "土星", "天王星", "海王星"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate方法，活动启动时调用
        super.onCreate(savedInstanceState); // 调用父类的onCreate方法
        setContentView(R.layout.activity_spinner_icon); // 设置活动的布局文件
        List<Map<String, Object>> list = new ArrayList<>(); // 创建一个列表，用于存储图标和名称

        for (int i = 0; i < iconArray.length; i++) { // 遍历图标数组
            Map<String, Object> item = new HashMap<>(); // 创建一个HashMap对象
            item.put("icon", iconArray[i]); // 将图标资源ID放入Map中
            item.put("name", starArray[i]); // 将名称放入Map中
            list.add(item); // 将Map添加到列表中
        }

        // 创建一个SimpleAdapter，连接数据和布局
        SimpleAdapter startAdapter = new SimpleAdapter(this,
                list, // 数据源
                R.layout.item_simple, // 列表项布局
                new String[]{"icon", "name"}, // Map中的键
                new int[]{R.id.iv_icon, R.id.tv_name}); // 布局中的视图ID

        Spinner sp_icon = findViewById(R.id.sp_icon); // 获取Spinner控件
        sp_icon.setAdapter(startAdapter); // 为Spinner设置适配器
        sp_icon.setSelection(0); // 默认选中第0项
        sp_icon.setOnItemSelectedListener(this); // 设置选项选中监听器
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // 当选择一个选项时调用
        ToastUtil.show(this, "你选择的是：" + starArray[position]); // 显示选择的星球名称
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // 当没有选项被选择时调用

    }
}
