package com.androidlearning.boris.familycentralcontroler.fragment91;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Created by poxiaoge on 2017/3/9.
 */

//用来管理播放列表

public class page91Fragment extends Fragment {


    private View rootView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab91, container, false);
        rootView = view;
        return view;
    }





}
