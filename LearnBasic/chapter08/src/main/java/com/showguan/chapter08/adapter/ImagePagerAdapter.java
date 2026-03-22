package com.showguan.chapter08.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.showguan.chapter08.ViewPageActivity;
import com.showguan.chapter08.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final ArrayList<GoodsInfo> mGoodsList;

    private List<ImageView> mViewList = new ArrayList<>();


    public ImagePagerAdapter(Context context, ArrayList<GoodsInfo> goodsInfos) {
        this.mContext = context;
        this.mGoodsList = goodsInfos;
        for (GoodsInfo goodsInfo : mGoodsList) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            view.setImageResource(goodsInfo.pic);
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    // 实例化指定位置的页面，并将其添加到容器中
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView item = mViewList.get(position);
        container.addView(item);
        return item;
    }

    // 从容器中销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }
}
