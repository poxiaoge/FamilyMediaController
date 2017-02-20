package com.androidlearning.boris.familycentralcontroler.fragment02;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Adapters.MyAdapter;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;
import com.androidlearning.boris.familycentralcontroler.R;
import com.androidlearning.boris.familycentralcontroler.Utils.CommandUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.GetTvListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.OpenMethodUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.Send2TVThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.BATTERY_SERVICE;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_MEDIA_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_MEDIA_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_MEDIA_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_MEDIA_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_IP;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_OUT_PORT;


/**
 * Created by boris on 2016/11/29.
 * TAB02---媒体界面的Fragment
 */

public class page02Fragment extends Fragment {

//    private GridView id_tab02_movieComputer;
//    private ImageButton id_tab02_movieComputerActionButton;
//    private static List<ImageAndText> data_list01;
//    private String[] names01 = new String[] {
//            "UNDER THE SKIN", "BLUE RUIN",
//            "FURY",           "INHERENT VICE",
//            "SAINT LAURENT",  "STILL THE WATER",
//            "ENEMY",          "THE GRAND BUDAPEST HOTEL",
//            "GONE GIRL",      "OTHER",
//            "PARTICLE FEVER"};


    private View rootView;
    private Context mContext;
    private ImageButton btn_tv_video;
    private ImageButton btn_tv_audio;
    private ImageButton btn_tv_image;
    private ImageButton btn_pc_video;
    private ImageButton btn_pc_audio;
    private ImageButton btn_pc_image;
    private ListView list_tv_videos;
    private ListView list_tv_audios;
    private ListView list_tv_images;
    private ListView list_pc_videos;
    private ListView list_pc_audios;
    private ListView list_pc_images;
    private Map<String, List<MediaItem>> tvMediaMap;
    private Map<String, List<MediaItem>> pcMediaMap;
    private List<MediaItem> tv_video_list = new ArrayList<>();
    private List<MediaItem> tv_audio_list = new ArrayList<>();
    private List<MediaItem> tv_image_list = new ArrayList<>();
    private List<MediaItem> pc_video_list = new ArrayList<>();
    private List<MediaItem> pc_audio_list = new ArrayList<>();
    private List<MediaItem> pc_image_list = new ArrayList<>();
    private MyAdapter<MediaItem> tv_video_adapter = null;
    private MyAdapter<MediaItem> tv_audio_adapter = null;
    private MyAdapter<MediaItem> tv_image_adapter = null;
    private MyAdapter<MediaItem> pc_video_adapter = null;
    private MyAdapter<MediaItem> pc_audio_adapter = null;
    private MyAdapter<MediaItem> pc_image_adapter = null;
    private final String tag = this.getClass().getSimpleName();
    private ListView[] listManager = new ListView[6];
    private ImageButton[] buttonManager = new ImageButton[6];


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        data_list01 = new ArrayList<>();
//        getData();
    }

//    private void getData() {
//        for(int i = 0; i < names01.length; i++) {
//            String path;
//            if(i < 9) {
//                path = "/sdcard/familycentralcontroler/movie0" + String.valueOf(i + 1) + ".jpg";
//            } else {
//                path = "/sdcard/familycentralcontroler/movie"  + String.valueOf(i + 1) + ".jpg";
//            }
//            data_list01.add(new ImageAndText(path, names01[i]));
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab02, container, false);
        rootView = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        new GetPcListThread("MEDIALIST",myHandler).start();
        new GetTvListThread("media",myHandler).start();
        initViews();
//        initEvents();
    }

    //    private void initEvents() {
//        id_tab02_movieComputer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast toast = Toast.makeText(getActivity(), names01[position], Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
//        id_tab02_movieComputerActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(id_tab02_movieComputer.getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT) {
//                    ViewGroup.LayoutParams layoutParams = id_tab02_movieComputer.getLayoutParams();
//                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    id_tab02_movieComputer.setLayoutParams(layoutParams);
//                    id_tab02_movieComputerActionButton.setBackgroundResource(R.drawable.shrink);
//                } else {
//                    ViewGroup.LayoutParams layoutParams = id_tab02_movieComputer.getLayoutParams();
//                    layoutParams.height = 0;
//                    id_tab02_movieComputer.setLayoutParams(layoutParams);
//                    id_tab02_movieComputerActionButton.setBackgroundResource(R.drawable.spread);
//                }
//            }
//        });
//    }



    private void initViews() {
//        id_tab02_movieComputer = (GridView) getActivity().findViewById(R.id.id_tab02_movieComputer);
//        id_tab02_movieComputerActionButton = (ImageButton) getActivity().findViewById(R.id.id_tab02_movieComputerActionButton);
//        ImageAndTextListAdapter adapter01 = new ImageAndTextListAdapter(getActivity(), data_list01, id_tab02_movieComputer);
//        id_tab02_movieComputer.setAdapter(adapter01);

        list_tv_videos = (ListView) rootView.findViewById(R.id.list_tv_videos);
        list_tv_audios = (ListView) rootView.findViewById(R.id.list_tv_audios);
        list_tv_images = (ListView) rootView.findViewById(R.id.list_tv_images);
        list_pc_videos = (ListView) rootView.findViewById(R.id.list_pc_videos);
        list_pc_audios = (ListView) rootView.findViewById(R.id.list_pc_audios);
        list_pc_images = (ListView) rootView.findViewById(R.id.list_pc_images);

        tv_video_adapter = new MediaAdapter((ArrayList<MediaItem>) tv_video_list);
        tv_audio_adapter = new MediaAdapter((ArrayList<MediaItem>) tv_audio_list);
        tv_image_adapter = new MediaAdapter((ArrayList<MediaItem>) tv_image_list);
        pc_video_adapter = new MediaAdapter((ArrayList<MediaItem>) pc_video_list);
        pc_audio_adapter = new MediaAdapter((ArrayList<MediaItem>) pc_audio_list);
        pc_image_adapter = new MediaAdapter((ArrayList<MediaItem>) pc_image_list);

        list_tv_videos.setAdapter(tv_video_adapter);
        list_tv_audios.setAdapter(tv_audio_adapter);
        list_tv_images.setAdapter(tv_image_adapter);
        list_pc_videos.setAdapter(pc_video_adapter);
        list_pc_audios.setAdapter(pc_audio_adapter);
        list_pc_images.setAdapter(pc_image_adapter);
        list_tv_videos.setOnItemClickListener(itemClickListener1);
        list_tv_audios.setOnItemClickListener(itemClickListener1);
        list_tv_images.setOnItemClickListener(itemClickListener1);
        list_pc_videos.setOnItemClickListener(itemClickListener1);
        list_pc_audios.setOnItemClickListener(itemClickListener1);
        list_pc_images.setOnItemClickListener(itemClickListener1);
        listManager[0] = list_tv_videos;
        listManager[1] = list_tv_audios;
        listManager[2] = list_tv_images;
        listManager[3] = list_pc_videos;
        listManager[4] = list_pc_audios;
        listManager[5] = list_pc_images;



        btn_tv_video = (ImageButton) rootView.findViewById(R.id.btn_tv_video);
        btn_tv_audio = (ImageButton) rootView.findViewById(R.id.btn_tv_audio);
        btn_tv_image = (ImageButton) rootView.findViewById(R.id.btn_tv_image);
        btn_pc_video = (ImageButton) rootView.findViewById(R.id.btn_pc_video);
        btn_pc_audio = (ImageButton) rootView.findViewById(R.id.btn_pc_audio);
        btn_pc_image = (ImageButton) rootView.findViewById(R.id.btn_pc_image);
        buttonManager[0] = btn_tv_video;
        buttonManager[1] = btn_tv_audio;
        buttonManager[2] = btn_tv_image;
        buttonManager[3] = btn_pc_video;
        buttonManager[4] = btn_pc_audio;
        buttonManager[5] = btn_pc_image;
        btn_tv_video.setOnClickListener(clickListener1);
        btn_tv_audio.setOnClickListener(clickListener1);
        btn_tv_image.setOnClickListener(clickListener1);
        btn_pc_video.setOnClickListener(clickListener1);
        btn_pc_audio.setOnClickListener(clickListener1);
        btn_pc_image.setOnClickListener(clickListener1);
    }

    public void bindData(String type) {
        switch (type) {
            case "tv":
                tvMediaMap = BaseApplication.getTVMediaMap();
                tv_video_list.clear();
                tv_audio_list.clear();
                tv_image_list.clear();
                tv_video_list.addAll(tvMediaMap.get("video"));
                tv_audio_list.addAll(tvMediaMap.get("audio"));
                tv_image_list.addAll(tvMediaMap.get("image"));
                tv_video_adapter.notifyDataSetChanged();
                tv_audio_adapter.notifyDataSetChanged();
                tv_image_adapter.notifyDataSetChanged();
                break;
            case "pc":
                pcMediaMap = BaseApplication.getPCMediaMap();
                pc_video_list.clear();
                pc_audio_list.clear();
                pc_image_list.clear();
                pc_video_list.addAll(pcMediaMap.get("video"));
                pc_audio_list.addAll(pcMediaMap.get("audio"));
                pc_image_list.addAll(pcMediaMap.get("image"));
                pc_video_adapter.notifyDataSetChanged();
                pc_audio_adapter.notifyDataSetChanged();
                pc_image_adapter.notifyDataSetChanged();
                break;
        }
    }

    public void expandListView(int id) {
        ListView list2 = listManager[id];
        ViewGroup.LayoutParams layoutParams2 = list2.getLayoutParams();
        Boolean flag = (layoutParams2.height != ViewGroup.LayoutParams.MATCH_PARENT) ? true : false;
        for (ListView list : listManager) {
            ViewGroup.LayoutParams layoutParams = list.getLayoutParams();
            layoutParams.height = 0;
            list.setLayoutParams(layoutParams);
        }
        for (ImageButton btn : buttonManager) {
            btn.setBackgroundResource(R.drawable.spread);
        }
        if (flag) {
            layoutParams2.height = ViewGroup.LayoutParams.MATCH_PARENT;
            list2.setLayoutParams(layoutParams2);
            buttonManager[id].setBackgroundResource(R.drawable.shrink);
        }
        else {
            layoutParams2.height = 0;
            list2.setLayoutParams(layoutParams2);
            buttonManager[id].setBackgroundResource(R.drawable.spread);
        }
    }



    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btn_tv_video):
                    expandListView(0);
                    break;
                case (R.id.btn_tv_audio):
                    expandListView(1);
                    break;
                case (R.id.btn_tv_image):
                    expandListView(2);
                    break;
                case (R.id.btn_pc_video):
                    expandListView(3);
                    break;
                case (R.id.btn_pc_audio):
                    expandListView(4);
                    break;
                case (R.id.btn_pc_image):
                    expandListView(5);
                    break;
            }
        }
    };

    ListView.OnItemClickListener itemClickListener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView mediaPathView = (TextView) view.findViewById(R.id.text_media_path);
            TextView mediaLocationView = (TextView) view.findViewById(R.id.text_media_location);
            switch (mediaLocationView.getText().toString()) {
                case ("tv"):
                    String cmd = JSON.toJSONString(CommandUtil.createOpenTvMediaCommand(mediaPathView.getText().toString()));
                    new Send2TVThread(cmd, TV_IP, TV_OUT_PORT).start();
                    break;
                case ("pc"):
                    OpenMethodUtil.showDialog("media", mediaPathView.getText().toString(), mContext);
                    break;
            }
        }
    };



    public class MediaAdapter extends MyAdapter<MediaItem>{
        public MediaAdapter(ArrayList<MediaItem> media_list) {
            super(media_list,R.layout.media_item_layout);
        }
        @Override
        public void bindView(ViewHolder holder, MediaItem obj) {
            holder.setText(R.id.text_media_name, obj.getName());
            holder.setText(R.id.text_media_path, obj.getPathName());
            holder.setText(R.id.text_media_location, obj.getLocation());
        }
    }

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_TV_MEDIA_OK:
                    bindData("tv");
                    Toast.makeText(mContext, "获取TV媒体成功！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_PC_MEDIA_OK:
                    bindData("pc");
                    Toast.makeText(mContext, "获取PC媒体成功！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_TV_MEDIA_FAIL:
                    Toast.makeText(mContext, "获取TV媒体失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_PC_MEDIA_FAIL:
                    Toast.makeText(mContext, "获取PC媒体失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };






}

