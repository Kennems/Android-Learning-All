package com.showguan.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.showguan.chapter08.R;
import com.showguan.chapter08.entity.Planet;
import com.showguan.chapter08.util.ToastUtil;

import java.util.List;


public class PlaneListWithButtonAdapter extends BaseAdapter { // 定义PlanetBaseAdapter类，继承自BaseAdapter

    private final Context mContext; // 上下文对象
    private final List<Planet> mPlanetList; // 存储Planet对象的列表

    // 构造方法，初始化上下文和列表
    public PlaneListWithButtonAdapter(Context mContext, List<Planet> mPlanetList) {
        this.mContext = mContext; // 初始化上下文
        this.mPlanetList = mPlanetList; // 初始化星球列表
    }

    @Override
    public int getCount() { // 返回列表项的数量
        return mPlanetList.size(); // 返回星球列表的大小
    }

    @Override
    public Object getItem(int position) { // 获取指定位置的列表项
        return mPlanetList.get(position); // 返回指定位置的Planet对象
    }

    @Override
    public long getItemId(int position) { // 获取指定位置项的ID
        return position; // 返回位置作为项的ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // 获取每个列表项的视图
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_with_button, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon); // 获取图标视图
            holder.tv_name = convertView.findViewById(R.id.tv_name); // 获取名称视图
            holder.tv_desc = convertView.findViewById(R.id.tv_desc); // 获取描述视图
            holder.btn_oper = convertView.findViewById(R.id.btn_oper);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        holder.btn_oper.setOnClickListener(v->{
            ToastUtil.show(mContext, "按钮被点击了");
        });

        return convertView;
//        // 根据布局文件item_base.xml生成转换视图对象
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_base, null);
//        ImageView iv_icon = view.findViewById(R.id.iv_icon); // 获取图标视图
//        TextView tv_name = view.findViewById(R.id.tv_name); // 获取名称视图
//        TextView tv_desc = view.findViewById(R.id.tv_desc); // 获取描述视图
//
//        // 给控件设置数据
//        Planet planet = mPlanetList.get(position); // 获取当前星球对象
//        iv_icon.setImageResource(planet.image); // 设置图标资源
//        tv_name.setText(planet.name); // 设置星球名称
//        tv_desc.setText(planet.desc); // 设置星球描述
//
//        return view; // 返回生成的视图
    }

    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
        public Button btn_oper;
    }
}
