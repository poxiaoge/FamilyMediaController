package com.androidlearning.boris.familycentralcontroler.Utils;

import android.net.Uri;
import android.util.Log;

import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by poxiaoge on 2017/3/9.
 */

public class ImageUtil {

    public static final String tag = "ImageUtil";

    public static void downloadSingleThumbnail(String urlString) {
        String filename = BaseApplication.rootAppPath + "/thumbnail/" + urlString.substring(urlString.lastIndexOf("/") + 1, urlString.length());
        Log.e(tag, "image path is: " + filename);
        File file = new File(filename);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




//    public Uri getImageURI(String path, File cache) throws Exception {
//        String name = MD5.getMD5(path) + path.substring(path.lastIndexOf("."));
//        File file = new File(cache, name);
//        // 如果图片存在本地缓存目录，则不去服务器下载
//        if (file.exists()) {
//            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
//        } else {
//            // 从网络上获取图片
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            if (conn.getResponseCode() == 200) {
//
//                InputStream is = conn.getInputStream();
//                FileOutputStream fos = new FileOutputStream(file);
//                byte[] buffer = new byte[1024];
//                int len = 0;
//                while ((len = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len);
//                }
//                is.close();
//                fos.close();
//                // 返回一个URI对象
//                return Uri.fromFile(file);
//            }
//        }
//        return null;
//    }




}
