package com.showguan.chapter08.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.showguan.chapter08.fragment.BillFragment;

public class BillPagerAdapter extends FragmentPagerAdapter {

    private final int mYear;
    private static final String TAG = "Kennem";

    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mYear = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, String.valueOf(position));
        int month = position + 1;
        String zeroMonth = month < 10 ? "0" + month : "" + month;
        String yearMonth = mYear + "-" + zeroMonth;
        Log.d(TAG, yearMonth);
        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "月份";
    }
}
