package com.androidlearning.boris.familycentralcontroler.fragment02;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by boris on 2016/12/9.
 * 异步获取图片
 */
public class AsyncImageLoader {

    private HashMap<String, SoftReference<Drawable>> imageCache;

    /**
     * 初始化缓存
     * */
    public AsyncImageLoader() {
        imageCache = new HashMap<>();
    }

    // *  加载图片
    //  1、缓存中有该图片，直接封装返回
    //  2、缓存中没有该图片，开启线程下载，返回null
    public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
        if(imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if(drawable != null) {
                return drawable;
            }
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imageCallback.imageLoaded((Drawable)msg.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    private Drawable loadImageFromUrl(String url) {
        // 加载内存图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(url, options);
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }

    public interface ImageCallback {
        void imageLoaded(Drawable imageDrawable, String imageUrl);
    }
}
