package com.androidlearning.boris.familycentralcontroler.fragment02;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Created by boris on 2016/12/11.
 * 根据指定视图初始化其中的控件并返回
 */

public class ViewCache {
    private View baseView;
    private TextView textView;
    private ImageView imageView;

    public ViewCache(View baseView) {
        this.baseView = baseView;
    }

    public TextView getTextView() {
        if(textView == null) {
            textView = (TextView) baseView.findViewById(R.id.movieNameText);
        }
        return textView;
    }

    public ImageView getImageView() {
        if(imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.movieImage);
        }
        return imageView;
    }
}