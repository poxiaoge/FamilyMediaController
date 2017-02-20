package com.androidlearning.boris.familycentralcontroler;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlearning.boris.familycentralcontroler.fragment01.page01Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment02.page02Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment03.page03Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment04.page04Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment05.page05Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment06.page06Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ViewPager id_ContentViewpager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments;

    private static final int page01 = 0;
    private static final int page02 = 1;
    private static final int page03 = 2;
    private static final int page04 = 3;
    private static final int page05 = 4;
    private static final int page06 = 5;

    private HorizontalScrollView id_tabBar;

    private LinearLayout id_tab01;
    private LinearLayout id_tab02;
    private LinearLayout id_tab03;
    private LinearLayout id_tab04;
    private LinearLayout id_tab05;
    private LinearLayout id_tab06;

    private ImageButton id_tab01_image;
    private ImageButton id_tab02_image;
    private ImageButton id_tab03_image;
    private ImageButton id_tab04_image;
    private ImageButton id_tab05_image;
    private ImageButton id_tab06_image;

    private TextView id_tab01_text;
    private TextView id_tab02_text;
    private TextView id_tab03_text;
    private TextView id_tab04_text;
    private TextView id_tab05_text;
    private TextView id_tab06_text;

    private Timer timer;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verifyStoragePermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        setSelect(page01);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * <summary>
     * 用于定时更新首页中部的图片
     * </summary>
     */
    private void startingTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = Order.refreshTab01MiddleImage_order;
                handler.sendMessage(msg);
            }
        }, 5000, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startingTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initEvent() {
        id_tab01.setOnClickListener(this);
        id_tab02.setOnClickListener(this);
        id_tab03.setOnClickListener(this);
        id_tab04.setOnClickListener(this);
        id_tab05.setOnClickListener(this);
        id_tab06.setOnClickListener(this);
        id_ContentViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void initView() {

        id_tabBar           = (HorizontalScrollView) findViewById(R.id.id_tabBar);
        id_ContentViewpager = (ViewPager)    findViewById(R.id.id_contentViewPager);
        id_tab01            = (LinearLayout) findViewById(R.id.id_tab01);
        id_tab02            = (LinearLayout) findViewById(R.id.id_tab02);
        id_tab03            = (LinearLayout) findViewById(R.id.id_tab03);
        id_tab04            = (LinearLayout) findViewById(R.id.id_tab04);
        id_tab05            = (LinearLayout) findViewById(R.id.id_tab05);
        id_tab06            = (LinearLayout) findViewById(R.id.id_tab06);
        id_tab01_image      = (ImageButton)  findViewById(R.id.id_tab01_image);
        id_tab02_image      = (ImageButton)  findViewById(R.id.id_tab02_image);
        id_tab03_image      = (ImageButton)  findViewById(R.id.id_tab03_image);
        id_tab04_image      = (ImageButton)  findViewById(R.id.id_tab04_image);
        id_tab05_image      = (ImageButton)  findViewById(R.id.id_tab05_image);
        id_tab06_image      = (ImageButton)  findViewById(R.id.id_tab06_image);
        id_tab01_text       = (TextView)     findViewById(R.id.id_tab01_text);
        id_tab02_text       = (TextView)     findViewById(R.id.id_tab02_text);
        id_tab03_text       = (TextView)     findViewById(R.id.id_tab03_text);
        id_tab04_text       = (TextView)     findViewById(R.id.id_tab04_text);
        id_tab05_text       = (TextView)     findViewById(R.id.id_tab05_text);
        id_tab06_text       = (TextView)     findViewById(R.id.id_tab06_text);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x / 4;
        ViewGroup.LayoutParams layoutParams01 = id_tab01.getLayoutParams();
        ViewGroup.LayoutParams layoutParams02 = id_tab02.getLayoutParams();
        ViewGroup.LayoutParams layoutParams03 = id_tab03.getLayoutParams();
        ViewGroup.LayoutParams layoutParams04 = id_tab04.getLayoutParams();
        ViewGroup.LayoutParams layoutParams05 = id_tab05.getLayoutParams();
        ViewGroup.LayoutParams layoutParams06 = id_tab06.getLayoutParams();
        layoutParams01.width = width;
        layoutParams02.width = width;
        layoutParams03.width = width;
        layoutParams04.width = width;
        layoutParams05.width = width;
        layoutParams06.width = width;
        layoutParams01.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams02.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams03.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams04.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams05.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams06.height = ViewGroup.LayoutParams.MATCH_PARENT;
        id_tab01.setLayoutParams(layoutParams01);
        id_tab02.setLayoutParams(layoutParams02);
        id_tab03.setLayoutParams(layoutParams03);
        id_tab04.setLayoutParams(layoutParams04);
        id_tab05.setLayoutParams(layoutParams05);
        id_tab06.setLayoutParams(layoutParams06);


        fragments = new ArrayList<>();
        page01Fragment tabContent01 = new page01Fragment();
        page02Fragment tabContent02 = new page02Fragment();
        page03Fragment tabContent03 = new page03Fragment();
        page04Fragment tabContent04 = new page04Fragment();
        page05Fragment tabContent05 = new page05Fragment();
        page06Fragment tabContent06 = new page06Fragment();
        fragments.add(tabContent01);
        fragments.add(tabContent02);
        fragments.add(tabContent03);
        fragments.add(tabContent04);
        fragments.add(tabContent05);
        fragments.add(tabContent06);

        // 用于传递参数（更新首页中动态切换的图片）
        handler = new MyHandler(tabContent01);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        };
        id_ContentViewpager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab01:
                setSelect(page01);
                break;
            case R.id.id_tab02:
                setSelect(page02);
                break;
            case R.id.id_tab03:
                setSelect(page03);
                break;
            case R.id.id_tab04:
                setSelect(page04);
                break;
            case R.id.id_tab05:
                setSelect(page05);
                break;
            case R.id.id_tab06:
                setSelect(page06);
                break;
        }
    }

    /**
     * 1. 当用户点击按钮时切换内容区、切换按钮显示效果
     * 2. 当用户滑动内容区进行切换时切换按钮显示效果
     * */
    private void setSelect(int pageNum) {
        resetImgText();
        if(id_ContentViewpager.getCurrentItem() != pageNum) {
            id_ContentViewpager.setCurrentItem(pageNum);
        }
        switch (pageNum) {
            case page01:
                id_tabBar.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                id_tab01_image.setImageResource(R.drawable.tab01_pressed);
                id_tab01_text.setTextColor(Color.rgb(230, 87, 87));
                break;
            case page02:
                id_tabBar.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                id_tab02_image.setImageResource(R.drawable.tab02_pressed);
                id_tab02_text.setTextColor(Color.rgb(230, 87, 87));
                break;
            case page03:
                id_tab03_image.setImageResource(R.drawable.tab03_pressed);
                id_tab03_text.setTextColor(Color.rgb(230, 87, 87));
                break;
            case page04:
                id_tab04_image.setImageResource(R.drawable.tab04_pressed);
                id_tab04_text.setTextColor(Color.rgb(230, 87, 87));
                break;
            case page05:
                id_tabBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                id_tab05_image.setImageResource(R.drawable.tab05_pressed);
                id_tab05_text.setTextColor(Color.rgb(230, 87, 87));
                break;
            case page06:
                id_tabBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                id_tab06_image.setImageResource(R.drawable.tab06_pressed);
                id_tab06_text.setTextColor(Color.rgb(230, 87, 87));
                break;
        }
    }

    /**
     * 将底部栏目涉及的图片按文字置为暗色
     * */
    private void resetImgText() {
        id_tab01_image.setImageResource(R.drawable.tab01_normal);
        id_tab02_image.setImageResource(R.drawable.tab02_normal);
        id_tab03_image.setImageResource(R.drawable.tab03_normal);
        id_tab04_image.setImageResource(R.drawable.tab04_normal);
        id_tab05_image.setImageResource(R.drawable.tab05_normal);
        id_tab06_image.setImageResource(R.drawable.tab06_normal);
        id_tab01_text.setTextColor(Color.rgb(153, 153, 153));
        id_tab02_text.setTextColor(Color.rgb(153, 153, 153));
        id_tab03_text.setTextColor(Color.rgb(153, 153, 153));
        id_tab04_text.setTextColor(Color.rgb(153, 153, 153));
        id_tab05_text.setTextColor(Color.rgb(153, 153, 153));
        id_tab06_text.setTextColor(Color.rgb(153, 153, 153));
    }

}
