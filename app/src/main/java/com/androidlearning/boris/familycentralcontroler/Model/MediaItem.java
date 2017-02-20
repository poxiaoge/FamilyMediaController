package com.androidlearning.boris.familycentralcontroler.Model;

import android.net.Uri;

/**
 * Created by poxiaoge on 2016/12/24.
 */

public class MediaItem {
    private String name;
    private String pathName;
    private Uri uri;
    private String location;
//    private String openMethod;


    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

//    public void setOpenMethod(String openMethod) {
//        this.openMethod = openMethod;
//    }
//
//    public String getOpenMethod() {
//        return openMethod;
//    }
}
