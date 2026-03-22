package com.showguan.chapter08.adapter; // 包名

import android.content.Context; // 导入Context类
import android.view.LayoutInflater; // 导入LayoutInflater类
import android.view.View; // 导入View类
import android.view.ViewGroup; // 导入ViewGroup类
import android.widget.BaseAdapter; // 导入BaseAdapter类
import android.widget.ImageView; // 导入ImageView类
import android.widget.TextView; // 导入TextView类

import com.showguan.chapter08.R; // 导入项目的R类
import com.showguan.chapter08.entity.Planet; // 导入自定义的Planet类

import java.util.List; // 导入List接口

public class PlanetBaseAdapter extends BaseAdapter { // 定义PlanetBaseAdapter类，继承自BaseAdapter

    private final Context mContext; // 上下文对象
    private final List<Planet> mPlanetList; // 存储Planet对象的列表

    // 构造方法，初始化上下文和列表
    public PlanetBaseAdapter(Context mContext, List<Planet> mPlanetList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_base, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon); // 获取图标视图
            holder.tv_name = convertView.findViewById(R.id.tv_name); // 获取名称视图
            holder.tv_desc = convertView.findViewById(R.id.tv_desc); // 获取描述视图
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
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
    }
}
