package com.androidlearning.boris.familycentralcontroler.fragment02;

/**
 * Created by boris on 2016/12/11.
 * 电影缩略图路径和名称
 */

public class ImageAndText {
    private String imageUrl;
    private String text;

    public ImageAndText(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text     = text;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getText() {
        return text;
    }
}