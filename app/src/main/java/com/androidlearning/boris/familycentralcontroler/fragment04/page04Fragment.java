package com.androidlearning.boris.familycentralcontroler.fragment04;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Created by boris on 2016/11/29.
 * TAB04---文件的Fragment
 */

public class page04Fragment extends Fragment {

    private SearchView id_tab04_searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab04, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        id_tab04_searchView = (SearchView) getActivity().findViewById(R.id.id_tab04_searchView);

    }
}
