package com.showguan.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.showguan.chapter08.R;
import com.showguan.chapter08.entity.BillInfo;

import java.util.List;

public class BillListAdapter extends BaseAdapter {


    private final List<BillInfo> mBillInfoList;
    private final Context mContext;

    public BillListAdapter(Context context, List<BillInfo> billInfoList) {
        this.mContext = context;
        this.mBillInfoList = billInfoList;
    }

    @Override
    public int getCount() {
        return mBillInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBillInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill, null);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BillInfo billInfo = mBillInfoList.get(position);
        viewHolder.tv_date.setText(billInfo.date);
        viewHolder.tv_remark.setText(billInfo.remark);
        viewHolder.tv_amount.setText(String.format("%s %d 元", billInfo.type == 0 ? "+" : "-" ,(int)billInfo.amount));
        return convertView;
    }

    public final class ViewHolder{
        public TextView tv_date;
        public TextView tv_remark;
        public TextView tv_amount;
    }

}
