package com.androidlearning.boris.familycentralcontroler.fragment06;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Created by boris on 2016/11/29.
 * TAB06---分享的Fragment
 */

public class page06Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab06, container, false);
    }
}
