package com.showguan.chapter08.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.showguan.chapter08.R;
import com.showguan.chapter08.entity.CartInfo;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context mContext;
    private List<CartInfo> mCartList;

    public CartAdapter(Context context, List<CartInfo> mCartList) {
        this.mContext = context;
        this.mCartList = mCartList;
    }

    @Override
    public int getCount() {
        return mCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_phone_name = convertView.findViewById(R.id.tv_phone_name);
            holder.tv_phone_desc = convertView.findViewById(R.id.tv_phone_desc);
            holder.tv_phone_count = convertView.findViewById(R.id.tv_phone_count);
            holder.tv_phone_price = convertView.findViewById(R.id.tv_phone_price);
            holder.tv_total_price = convertView.findViewById(R.id.tv_total_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartInfo info = mCartList.get(position);
        holder.iv_thumb.setImageURI(Uri.parse(info.goods.picPath));
        holder.tv_phone_name.setText(info.goods.name);
        holder.tv_phone_desc.setText(info.goods.description);
        holder.tv_phone_count.setText(String.valueOf(info.count));
        holder.tv_phone_price.setText(String.valueOf(info.goods.price));
        // 总价
        holder.tv_total_price.setText(String.valueOf(info.count * info.goods.price));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_phone_name;
        public TextView tv_phone_desc;
        public TextView tv_phone_count;
        public TextView tv_phone_price;
        public TextView tv_total_price;
    }
}
