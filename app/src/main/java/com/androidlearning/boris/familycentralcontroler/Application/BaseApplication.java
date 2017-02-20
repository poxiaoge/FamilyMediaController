package com.androidlearning.boris.familycentralcontroler.Application;

import android.app.Application;


import com.androidlearning.boris.familycentralcontroler.Model.AppItem;
import com.androidlearning.boris.familycentralcontroler.Model.DeviceItem;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;
import com.androidlearning.boris.familycentralcontroler.Model.PCCommandItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poxiaoge on 2016/12/24.
 */

public class BaseApplication extends Application {

//    public static final String TV_IP = "192.168.2.148";
    public static  String TV_IP = "192.168.1.106";
    public static final int TV_IN_PORT = 12547;
    public static final int TV_OUT_PORT = 12548;
    public static  String PC_IP = "192.168.1.126";
    public static final int PC_IN_PORT = 12549;
    public static final int PC_OUT_PORT = 8888;

    //全局的handler消息定义
    public static final int GET_TV_APP_OK = 1;
    public static final int GET_TV_APP_FAIL = 2;
    public static final int GET_TV_MEDIA_OK = 3;
    public static final int GET_TV_MEDIA_FAIL = 4;
    public static final int GET_PC_APP_OK = 5;
    public static final int GET_PC_APP_FAIL = 6;
    public static final int GET_PC_MEDIA_OK = 7;
    public static final int GET_PC_MEDIA_FAIL = 8;
    public static final int GET_PC_DEVICE_OK = 9;
    public static final int GET_PC_DEVICE_FAIL = 10;

    private static List<AppItem> mTVAppList = new ArrayList<>();
    private static List<AppItem> mPCAppList = new ArrayList<>();

    private static Map<String, List<MediaItem>> mTVMediaMap = new HashMap<>();
    private static Map<String, List<MediaItem>> mPCMediaMap = new HashMap<>();

    public static DeviceItem chosenDevice = new DeviceItem();
    public static List<DeviceItem> mDeviceList = new ArrayList<>();
    public static DeviceItem lastChosenDevice;

    public static PCCommandItem lastPCCommand;



    public static Boolean tvAppOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean tvMediaOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean pcAppOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean pcMediaOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean getDeviceOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean chooseDeviceOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean lastChosenDeviceOK = Boolean.valueOf(String.valueOf(false));

    public static final int SOCKET_TIMEOUT = 5000;

    @Override
    public void onCreate() {
        super.onCreate();
    }




    public static void setDeviceList(List<DeviceItem> list1) {
        BaseApplication.mDeviceList.clear();
        BaseApplication.mDeviceList.addAll(list1);
        BaseApplication.getDeviceOK=true;
    }

    public static List<DeviceItem> getDeviceList(){
        return BaseApplication.mDeviceList;
    }

    public static void setChosenDevice(DeviceItem device) {
        BaseApplication.chosenDevice = device;
        BaseApplication.chooseDeviceOK=true;
    }



    public static void setTVAppList(List<AppItem> list1) {
        BaseApplication.mTVAppList.clear();
        BaseApplication.mTVAppList.addAll(list1);
        BaseApplication.tvAppOK = true;
    }

    public static List<AppItem> getTVAppList() {
        return BaseApplication.mTVAppList;
    }

    public static void setPCAppList(List<AppItem> list1) {
        BaseApplication.mPCAppList.clear();
        BaseApplication.mPCAppList.addAll(list1);
        BaseApplication.pcAppOK = true;
    }

    public static List<AppItem> getPCAppList() {
        return BaseApplication.mPCAppList;
    }

    public static void setTVMediaMap(Map<String, List<MediaItem>> mediaMap) {
        BaseApplication.mTVMediaMap.clear();
        BaseApplication.mTVMediaMap.putAll(mediaMap);
        BaseApplication.tvMediaOK = true;
    }

    public static Map<String, List<MediaItem>> getTVMediaMap() {
        return BaseApplication.mTVMediaMap;
    }

    public static void setPCMediaMap(Map<String,List<MediaItem>> mediaMap) {
        BaseApplication.mPCMediaMap.clear();
        BaseApplication.mPCMediaMap.putAll(mediaMap);
        BaseApplication.pcMediaOK = true;
    }

    public static Map<String, List<MediaItem>> getPCMediaMap() {
        return BaseApplication.mPCMediaMap;
    }




}
