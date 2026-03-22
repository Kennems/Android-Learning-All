package com.showguan.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.showguan.chapter08.R;
import com.showguan.chapter08.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class LaunchPagerAdapter extends PagerAdapter{

    private Context mContext;
    private int[] mImageArray;
    private List<View> mViewList = new ArrayList<>();

    public LaunchPagerAdapter(Context context, int[] imageArray) {
        this.mContext = context;
        this.mImageArray = imageArray;
        for (int i = 0; i < mImageArray.length; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_launch, null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_launch = view.findViewById(R.id.btn_launch);
            iv_launch.setImageResource(imageArray[i]);

            // 每个页面都分配一组对应的单选按钮
            for (int j = 0; j < mImageArray.length; j++) {
                RadioButton radio = new RadioButton(context);
                radio.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                radio.setPadding(10, 10, 10, 10);
                rg_indicate.addView(radio);
            }
            // 使当前页面的单选按钮被选中
//            ((RadioButton) rg_indicate.getChildAt(i)).setChecked(true);
            RadioButton childAt = (RadioButton) rg_indicate.getChildAt(i);
            childAt.setChecked(true);


            if (i == mImageArray.length - 1) {
                btn_launch.setVisibility(View.VISIBLE);
                btn_launch.setOnClickListener(v->{
                    ToastUtil.show(mContext, "点击开始美好生活");
                });
            }

            mViewList.add(view);
        }
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View item = mViewList.get(position);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }

}
