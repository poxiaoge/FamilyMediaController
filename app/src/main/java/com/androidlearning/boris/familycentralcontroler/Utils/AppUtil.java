package com.androidlearning.boris.familycentralcontroler.Utils;

import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;

/**
 * Created by poxiaoge on 2017/3/10.
 */

public class AppUtil {


    public static String url2imgname(String url) {
        return BaseApplication.rootAppPath+"/thumbnail/"
                + url.substring(url.lastIndexOf("/") + 1, url.length());
    }



}
