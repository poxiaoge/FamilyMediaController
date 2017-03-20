package com.androidlearning.boris.familycentralcontroler.Application;

import android.app.Application;


import com.androidlearning.boris.familycentralcontroler.Model.AppItem;
import com.androidlearning.boris.familycentralcontroler.Model.DeviceItem;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;
import com.androidlearning.boris.familycentralcontroler.Model.PCCommandItem;
import com.androidlearning.boris.familycentralcontroler.Utils.OpenMethodUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poxiaoge on 2016/12/24.
 */

public class BaseApplication extends Application {

//    public static final String TV_IP = "192.168.2.148";
    public static  String TV_IP = "192.168.1.113";
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

    public static final int GET_TV_FOLDER_OK = 11;
    public static final int GET_TV_FOLDER_FAIL = 12;
    public static final int GET_PC_FOLDER_OK = 13;
    public static final int GET_PC_FOLDER_FAIL = 14;

    //TODO:3.20上午
    public static final int GET_DLNA_LIST_OK = 15;
    public static final int GET_DLNA_LIST_FAIL = 16;


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

    //TODO:3.20上午
    public static Boolean dlnaListOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean dlnaOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean miracastOK = Boolean.valueOf(String.valueOf(false));
    public static Boolean rdpOK = Boolean.valueOf(String.valueOf(false));



    public static final int SOCKET_TIMEOUT = 5000;

//    ///////////////////////////////////////////////////////////////////////////////////////////////

    public static final int ADD_DEPTH = 0;
    public static final int DELETE_DEPTH = 1;
    public static final int JUMP_2 = 2;
    public static final int JUMP_1 = 3;

    public static String rootAppPath;
    public static List<MediaItem> currentMediaList = new ArrayList<>();
    public static String requestPath = "/";
    public static String requestType = "all";
    public static int requestDepthChange = JUMP_1;
    public static String requestLocation = "mobile";

    //TODO:3.20上午
    public static List<String> dlnaList = new ArrayList<>();
    static List<String> appMethodList = new ArrayList<>();
    static List<String> mediaMethodList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            rootAppPath = getExternalCacheDir().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    //TODO:3.20上午
    public static List<String> setDlnaList(List<String> list){
        dlnaList.clear();
        dlnaList.addAll(list);
        BaseApplication.dlnaListOK=true;
        return dlnaList;
    }
    //TODO:3.20上午
    public static void initMethodList() {
        OpenMethodUtil.appList=null;
        OpenMethodUtil.mediaList=null;
        appMethodList.clear();
        mediaMethodList.clear();

        if (BaseApplication.miracastOK) {
            appMethodList.add("miracast");
            mediaMethodList.add("miracast");
        }
        if (BaseApplication.rdpOK) {
            appMethodList.add("rdp");
            mediaMethodList.add("rdp");
        }
        if (BaseApplication.dlnaOK) {
            mediaMethodList.add("dlna");
        }else {
            if (BaseApplication.dlnaListOK) {
                mediaMethodList.add("dlna");
            }
        }
        OpenMethodUtil.appList = appMethodList.toArray(new String[appMethodList.size()]);
        OpenMethodUtil.mediaList = mediaMethodList.toArray(new String[mediaMethodList.size()]);
    }



}
