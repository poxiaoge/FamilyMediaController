package com.androidlearning.boris.familycentralcontroler.fragment01;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Activities.DevicesActivity;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.DeviceItem;
import com.androidlearning.boris.familycentralcontroler.R;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.PreferenceUtil;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.chosenDevice;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.lastChosenDevice;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.lastChosenDeviceOK;


/**
 * Created by boris on 2016/11/29.
 * TAB01---首页的Fragment
 */

public class page01Fragment extends Fragment {

    private View rootView;
    private ImageView[] imageViews;
    private RelativeLayout id_tab01_middle;
    private LinearLayout id_tab01_middleIndicator;
    private LinearLayout id_tab01_middleComputer;
    private ViewPager id_tab01_middleViewpager;
    private PagerAdapter adapter;
    private int[] imageArr = new int[] {R.drawable.test_ironman,
                                        R.drawable.test_lv,
                                        R.drawable.test_sishi,
                                        R.drawable.test_spider};
    private boolean isInTouch = false;

    private ImageButton btn_get_devices;
    private LinearLayout btn_open_recent;
    private TextView txt_last_chosen_device_name;
    private TextView txt_last_chosen_device_able;
    private final String tag = this.getClass().getSimpleName();
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, container, false);
        rootView = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();
        new GetPcListThread("DEVICELIST",myHandler).start();
        initView();
        initEvent();

    }

    private void initEvent() {
        id_tab01_middleViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        id_tab01_middleViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isInTouch = true;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        isInTouch = false;
                        break;
                }
                return false;
            }
        });

        id_tab01_middleComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), computerMonitorActivity.class);
                startActivity(intent);
            }
        });
    }



    private void setIndicator(int position) {
        position %= imageArr.length;
        imageViews[position].setBackgroundResource(R.drawable.shape_origin_point_pink);
        for (int i = 0; i < imageArr.length; i++) {
            if(position != i) {
                imageViews[i].setBackgroundResource(R.drawable.shape_origin_point_white);
            }
        }
    }

    private void initView() {
        id_tab01_middleViewpager = (ViewPager) rootView.findViewById(R.id.id_tab01_middleViewpager);
        id_tab01_middleIndicator = (LinearLayout) rootView.findViewById(R.id.id_tab01_middleIndicator);
        id_tab01_middle = (RelativeLayout) rootView.findViewById(R.id.id_tab01_middle);
        id_tab01_middleComputer = (LinearLayout) getActivity().findViewById(R.id.id_tab01_middleComputer);

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int height = size.x / 4;
        ViewGroup.LayoutParams layoutParams = id_tab01_middle.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = height;
        id_tab01_middle.setLayoutParams(layoutParams);

        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return imageArr.length + 200;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_image01, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPager);
                position %= imageArr.length;
                imageView.setImageResource(imageArr[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void finishUpdate(ViewGroup container) {
                int position = id_tab01_middleViewpager.getCurrentItem();
                if(position == 0) {
                    id_tab01_middleViewpager.setCurrentItem(imageArr.length, false);
                } else if(position == imageArr.length + 199) {
                    id_tab01_middleViewpager.setCurrentItem(1, false);
                }

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView)object);
            }

        };
        id_tab01_middleViewpager.setAdapter(adapter);

        id_tab01_middleViewpager.setCurrentItem(imageArr.length);
        initIndicator();


        btn_get_devices = (ImageButton) rootView.findViewById(R.id.btn_get_devices);
        btn_open_recent = (LinearLayout) rootView.findViewById(R.id.btn_open_recent);
        txt_last_chosen_device_name = (TextView) rootView.findViewById(R.id.txt_last_chosen_device_name);
        txt_last_chosen_device_able = (TextView) rootView.findViewById(R.id.txt_last_chosen_device_able);
        btn_get_devices.setOnClickListener(clickListener1);
        btn_open_recent.setOnClickListener(clickListener1);


    }

    private void initIndicator() {
        imageViews = new ImageView[imageArr.length];
        for(int i = 0; i <imageArr.length; i++) {
            imageViews[i] = new ImageView(getActivity());
            if(i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.shape_origin_point_pink);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.shape_origin_point_white);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 0, 0, 0);
                imageViews[i].setLayoutParams(layoutParams);
            }
            id_tab01_middleIndicator.addView(imageViews[i]);
        }
    }

    public void refreshMiddle() {
        if(!isInTouch) {
            int position = id_tab01_middleViewpager.getCurrentItem();
            id_tab01_middleViewpager.setCurrentItem(position + 1, true);
        }
    }




    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btn_get_devices):
                    Intent i = new Intent(mContext, DevicesActivity.class);
                    Log.e(tag, "btn_get_devices");
                    startActivityForResult(i, 666);
                    break;
                case (R.id.btn_open_recent):
                    if (lastChosenDeviceOK) {
                        setDevice(lastChosenDevice, false);
                        txt_last_chosen_device_name.setTextColor(getResources().getColor(R.color.colorChosen));
//                        btn_open_recent.setTextColor(getResources().getColor(R.color.colorChosen));
                        Log.e(tag, "btn_open_recent");
                    } else {
                        Toast.makeText(mContext, "上次使用的设备暂时不可用。请先选择投放屏幕后再进行其它操作！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void setDevice(DeviceItem chosenDevice, Boolean dataChanged) {
        BaseApplication.setChosenDevice(chosenDevice);
        BaseApplication.TV_IP = chosenDevice.getIp();
        if(dataChanged) {
            String lastChosenDeviceJsonString = JSON.toJSONString(chosenDevice);
            new PreferenceUtil(mContext.getApplicationContext()).write("lastChosenDevice", lastChosenDeviceJsonString);
            txt_last_chosen_device_name.setText(chosenDevice.getDeviceName());
            txt_last_chosen_device_name.setTextColor(getResources().getColor(R.color.colorLastChosenOK));
            txt_last_chosen_device_able.setText("可用");
//            btn_open_recent.setText("上次使用的屏幕可用:\n" + chosenDevice.getDeviceName());
//            btn_open_recent.setTextColor(getResources().getColor(R.color.colorLastChosenOK));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(tag, "before set device");
        if(resultCode == 999 && requestCode == 666){
            Bundle b = data.getExtras();
            DeviceItem chosenDevice = (DeviceItem) b.getSerializable("device");
            setDevice(chosenDevice,true);
        }
    }

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_PC_DEVICE_OK:
                    Toast.makeText(mContext, "获取设备列表成功！",Toast.LENGTH_SHORT).show();
                    String deviceString = new PreferenceUtil(mContext.getApplicationContext()).read("lastChosenDevice");
                    if (deviceString != null && !deviceString.equals("")) {
                        lastChosenDevice = JSON.parseObject(deviceString, DeviceItem.class);
                        for (DeviceItem device : BaseApplication.mDeviceList) {
                            if (lastChosenDevice.getUuid().equals(device.getUuid())) {
                                txt_last_chosen_device_name.setText(lastChosenDevice.getDeviceName());
                                txt_last_chosen_device_name.setTextColor(getResources().getColor(R.color.colorLastChosenOK));
                                txt_last_chosen_device_able.setText("可用");
//                                btn_open_recent.setText("上次使用的屏幕可用:\n" + lastChosenDevice.getDeviceName());
//                                btn_open_recent.setTextColor(getResources().getColor(R.color.colorLastChosenOK));
                                lastChosenDeviceOK = true;
                                break;
                            }
                        }
                    }
                    break;
                case GET_PC_DEVICE_FAIL:
                    Toast.makeText(mContext, "获取屏幕列表失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };








}
