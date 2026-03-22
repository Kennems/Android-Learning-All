package com.showguan.chapter08.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showguan.chapter08.R;

public class StaticFragment extends Fragment {

    private static final String TAG = "fragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "Fragment onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Fragment onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Fragment onDetach: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment onCreateView: ");
        return inflater.inflate(R.layout.fragment_static, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment onActivityCreated: ");
    }

}