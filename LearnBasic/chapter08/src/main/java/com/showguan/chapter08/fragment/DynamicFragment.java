package com.showguan.chapter08.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.showguan.chapter08.R;

public class DynamicFragment extends Fragment {

    /**
     * 静态工厂方法，用于创建新的 DynamicFragment 实例并传递参数
     * @param position 位置索引
     * @param pic_id 图片资源 ID
     * @param desc 描述文本
     * @return 新的 DynamicFragment 实例
     */
    public static DynamicFragment newInstance(int position, int pic_id, String desc) {
        DynamicFragment fragment = new DynamicFragment();
        // 参数打包
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("image_id", pic_id);
        args.putString("desc", desc);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 创建并返回该 fragment 的视图层次结构
     * @param inflater 用于实例化布局 XML 文件的布局填充器对象
     * @param container 父级视图，null 表示没有父级视图
     * @param savedInstanceState 用于保存的状态
     * @return 返回 fragment 的根视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 使用给定的布局文件实例化视图
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);

        // 获取传递的参数
        Bundle arguments = getArguments();
        if(arguments != null){
            // 获取并设置 ImageView 和 TextView 的内容
            ImageView iv_pic = view.findViewById(R.id.iv_pic);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            iv_pic.setImageResource(arguments.getInt("image_id", R.drawable.diqiu));
            tv_desc.setText(arguments.getString("desc", ""));
        }
        return view;
    }
}
