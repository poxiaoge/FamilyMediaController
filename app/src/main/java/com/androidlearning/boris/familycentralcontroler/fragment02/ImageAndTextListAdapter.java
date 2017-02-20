package com.androidlearning.boris.familycentralcontroler.fragment02;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlearning.boris.familycentralcontroler.MainActivity;
import com.androidlearning.boris.familycentralcontroler.R;

import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * Created by boris on 2016/12/9.
 */
public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {

    private Context context;
    private GridView gridView;
    private AsyncImageLoader asyncImageLoader;

    public ImageAndTextListAdapter(Context context, List<ImageAndText> list, GridView gridView) {
        super(context, 0, list);
        this.gridView = gridView;
        this.context = context;
        asyncImageLoader = new AsyncImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        View itemView = convertView;
        ViewCache viewCache;
        if(itemView == null) {
            itemView = activity.getLayoutInflater().inflate(R.layout.item02, null);
            viewCache = new ViewCache(itemView);
            itemView.setTag(viewCache);
        } else {
            viewCache = (ViewCache) itemView.getTag();
        }

        final ImageAndText imageAndText = getItem(position);
        String imageUrl = imageAndText.getImageUrl();
        ImageView imageView = viewCache.getImageView();
        final TextView  textView  = viewCache.getTextView();
        imageView.setTag(imageUrl);
        // 根据路径加载图片
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);
                if(imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                    textView.setText(imageAndText.getText());
                }
            }
        });

        if(cachedImage == null) {
            imageView.setImageResource(R.drawable.loading);
            textView.setText("loading...");
        } else {
            imageView.setImageDrawable(cachedImage);
            textView.setText(imageAndText.getText());
        }

        return itemView;
    }
}
