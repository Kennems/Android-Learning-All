package com.showguan.chapter08.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.showguan.chapter08.LaunchImproveActivity;
import com.showguan.chapter08.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentPagerAdapter {

    private final int[] mLaunchImageArray;

    public LaunchImproveAdapter(@NonNull FragmentManager fm, int[] launchImageArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mLaunchImageArray = launchImageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFragment.newInstance(mLaunchImageArray.length, position, mLaunchImageArray[position]);
    }

    @Override
    public int getCount() {
        return mLaunchImageArray.length;
    }
}
