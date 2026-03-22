package com.showguan.chapter08.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.showguan.chapter08.R;
import com.showguan.chapter08.ShoppingChannelActivity;
import com.showguan.chapter08.entity.GoodsInfo;

import java.util.List;

public class GoodsGridAdapter extends BaseAdapter {

    public Context mContext;
    public List<GoodsInfo> mGoodsInfo;
    private AddCartListener mAddCartListener;


    public GoodsGridAdapter(Context mContext, List<GoodsInfo> mGoodsInfo, AddCartListener addCartListener) {
        this.mContext = mContext;
        this.mGoodsInfo = mGoodsInfo;
        this.mAddCartListener = addCartListener;
    }

    @Override
    public int getCount() {
        return mGoodsInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsInfo goodsInfo = mGoodsInfo.get(position);
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            // 将 XML 布局文件扩展为相应的 View 对象的类
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.btn_add = convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsInfo info = mGoodsInfo.get(position);

        holder.iv_thumb.setImageURI(Uri.parse(info.picPath));
        holder.tv_name.setText(info.name);
        holder.tv_price.setText(String.valueOf(info.price));
        holder.btn_add.setOnClickListener(v -> {
//            ShoppingChannelActivity activity = (ShoppingChannelActivity) mContext;
//            activity.addToCart(info.id, info.name);
            mAddCartListener.addToCart(info.id, info.name);
        });


        return convertView; // 最后一定要返回View
    }
    public final class ViewHolder{
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_price;
        public Button btn_add;
    }


    public interface AddCartListener{
        abstract void addToCart(int goodsId, String goodsName);
    }
}
