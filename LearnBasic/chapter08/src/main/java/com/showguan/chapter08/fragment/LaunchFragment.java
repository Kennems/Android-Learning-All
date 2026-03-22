package com.showguan.chapter08.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.showguan.chapter08.R;
import com.showguan.chapter08.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFragment extends Fragment {

    private Context mContext;

    public static LaunchFragment newInstance(int count, int position, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle arguments = getArguments();
        mContext = getContext();
        int count = arguments.getInt("count", 0);
        int position = arguments.getInt("position", 0);
        int image_id = arguments.getInt("image_id", 0);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_launch, container, false);
        ImageView iv_launch = view.findViewById(R.id.iv_launch);
        RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
        Button btn_launch = view.findViewById(R.id.btn_launch);
        iv_launch.setImageResource(image_id);

        // 每个页面都分配一组对应的单选按钮
        for (int j = 0; j < count; j++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            radio.setPadding(10, 10, 10, 10);
            rg_indicate.addView(radio);
        }
        // 使当前页面的单选按钮被选中
//            ((RadioButton) rg_indicate.getChildAt(i)).setChecked(true);
        RadioButton childAt = (RadioButton) rg_indicate.getChildAt(position);
        childAt.setChecked(true);


        if (position == count - 1) {
            btn_launch.setVisibility(View.VISIBLE);
            btn_launch.setOnClickListener(v -> {
                ToastUtil.show(mContext, "点击开始美好生活");
            });
        }

        return view;
    }
}