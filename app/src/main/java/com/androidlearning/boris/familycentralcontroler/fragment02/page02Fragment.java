package com.androidlearning.boris.familycentralcontroler.fragment02;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Adapters.MyAdapter;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;
import com.androidlearning.boris.familycentralcontroler.R;
import com.androidlearning.boris.familycentralcontroler.Utils.CommandUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcFolderThread;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.GetTvFolderThread;
import com.androidlearning.boris.familycentralcontroler.Utils.OpenMethodUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.Send2TVThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.ADD_DEPTH;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.DELETE_DEPTH;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_DLNA_LIST_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_FOLDER_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_FOLDER_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_FOLDER_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_FOLDER_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.JUMP_1;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.JUMP_2;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_IP;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_OUT_PORT;


/**
 * Created by boris on 2016/11/29.
 * TAB02---媒体界面的Fragment
 */

public class page02Fragment extends Fragment {


    private View rootView;
    private Context mContext;

//////////////////////////////////////////////////////////////////////////////////////////////////////

    ImageButton btn_return;
    Button btn_show_pc;
    Button btn_show_tv;
    Button btn_add_custom_folder;
    Button btn_addto_playlist;
    Button btn_create_playlist;
    Button btn_select_all;
    LinearLayout layout_playlist;

    ListView list_medias;
    List<MediaItem> mediaList = new ArrayList<>();
    MyAdapter<MediaItem> mediaAdapter;
    List<MediaItem> rootMediaList = new ArrayList<>();


    String currentPath = "/";
    int currentDepth = 1;
    String currentLocation = "mobile";
    String currentType = "all";

    Stack<String> pathStack = new Stack<>();





//TODO:每次都请求，会增加延迟。用栈的话会增大内存占用，如果路径过深的话可能栈溢出。
//    Stack<List<MediaItem>> listStack = new Stack<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab0201, container, false);
        rootView = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //TODO:3.20上午
        new GetPcListThread("DLNALIST",myHandler).start();
//        new GetTvListThread("media",myHandler).start();
        initViews();
    }




    private void createRootList() {
        MediaItem rootVideoItem = new MediaItem();
        rootVideoItem.setName("Video");
        rootVideoItem.setPathName("/Video");
        rootVideoItem.setLocation("mobile");
        rootVideoItem.setType("video");
        rootVideoItem.setIsFolder(true);


        MediaItem rootAudioItem = new MediaItem();
        rootAudioItem.setName("Audio");
        rootAudioItem.setPathName("/Audio");
        rootAudioItem.setLocation("mobile");
        rootAudioItem.setType("audio");
        rootAudioItem.setIsFolder(true);

        MediaItem rootImageItem = new MediaItem();
        rootImageItem.setName("Image");
        rootImageItem.setPathName("/Image");
        rootImageItem.setLocation("mobile");
        rootImageItem.setType("image");
        rootImageItem.setIsFolder(true);

        rootMediaList.clear();
        rootMediaList.add(rootVideoItem);
        rootMediaList.add(rootAudioItem);
        rootMediaList.add(rootImageItem);
    }


    private void initViews() {

        createRootList();
        mediaList.clear();
        mediaList.addAll(rootMediaList);

//        listStack.push(rootMediaList);

        btn_return = (ImageButton) rootView.findViewById(R.id.btn_return);
        btn_show_pc = (Button) rootView.findViewById(R.id.btn_show_pc);
        btn_show_tv = (Button) rootView.findViewById(R.id.btn_show_tv);
        btn_add_custom_folder = (Button) rootView.findViewById(R.id.btn_add_custom_folder);
        list_medias = (ListView) rootView.findViewById(R.id.list_medias);
        btn_addto_playlist = (Button) rootView.findViewById(R.id.btn_addto_playlist);
        btn_create_playlist = (Button) rootView.findViewById(R.id.btn_create_playlist);
        btn_select_all = (Button) rootView.findViewById(R.id.btn_select_all);
        layout_playlist = (LinearLayout) rootView.findViewById(R.id.layout_playlist);


        btn_return.setOnClickListener(clickListener1);
        btn_show_pc.setOnClickListener(clickListener1);
        btn_show_tv.setOnClickListener(clickListener1);
        btn_add_custom_folder.setOnClickListener(clickListener1);
        btn_addto_playlist.setOnClickListener(clickListener1);
        btn_create_playlist.setOnClickListener(clickListener1);
        btn_select_all.setOnClickListener(clickListener1);

        mediaAdapter = new MediaAdapter((ArrayList<MediaItem>) mediaList);
        list_medias.setAdapter(mediaAdapter);
        list_medias.setOnItemClickListener(itemClickListener1);
        list_medias.setOnItemLongClickListener(itemLongClickListener1);
    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_return:
//                    String currentPath = "/";
//                    int currentDepth = 1;

                    //currentPath = "/Video" (真实path可能是"c://a")
                    //currentDepth = 2;

                    //currentPath = "/data/TVServer/cache/video"
                    //currentDepth = 2;

                    //currentPath = "C://a//b"
                    //currentDepth = 3;

                    //currentPath = "/data/TVServer/cache/video/abc"
                    //currentDepth = 3;

                    //currentPath = "C://a//b//c.mp4"
                    //currentDepth = 4;

                    //currentPath = "/data/TVServer/cache/video/abc/def.mp4"
                    //currentDepth = 4;

//                    String currentPath = "/";
//                    int currentDepth = 1;
//                    String currentLocation = "mobile";
//                    String currentType = "all";
                    BaseApplication.requestDepthChange = DELETE_DEPTH;
                    Log.e("test return", "currentDepth: " + currentDepth);
                    Log.e("test return", "currentPath: " + currentPath);
                    Log.e("test return", "currentType: " + currentType);
                    if (currentDepth <= 2) {
                        mediaList.clear();
                        mediaList.addAll(rootMediaList);
                        mediaAdapter.notifyDataSetChanged();

                        currentPath = "/";
                        currentDepth = 1;
                        currentLocation = "mobile";
                        currentType = "all";

                        //根目录下隐藏按钮
                        btn_return.setVisibility(View.INVISIBLE);
                        btn_show_pc.setVisibility(View.INVISIBLE);
                        btn_show_tv.setVisibility(View.INVISIBLE);

                    }else if(currentDepth == 3) {
                        BaseApplication.requestDepthChange = JUMP_2;
                        if (currentLocation.equals("tv")) {
                            getRemoteFolder("tv",currentType,"root",true);
                        }
                        if (currentLocation.equals("pc")) {
                            getRemoteFolder("pc",currentType,"root",true);
                        }
                    }else{
//                        File f = new File(currentPath);
                        String parentPath = pathStack.peek();
                        BaseApplication.requestDepthChange = DELETE_DEPTH;
                        if (currentLocation.equals("tv")) {
                            getRemoteFolder("tv",currentType,parentPath,false);
                        }
                        if (currentLocation.equals("pc")) {
                            getRemoteFolder("pc",currentType,parentPath,false);
                        }

                    }
                    break;
                case R.id.btn_show_pc:
                    BaseApplication.requestDepthChange = JUMP_2;
                    getRemoteFolder("pc",currentType,"",true);
                    break;
                case R.id.btn_show_tv:
                    BaseApplication.requestDepthChange = JUMP_2;
                    getRemoteFolder("tv",currentType,"",true);
                    break;
                case R.id.btn_add_custom_folder:
                    Log.e("clicklinstener", "in btn_add_custom_folder");
                    break;
                case R.id.btn_addto_playlist:
                    break;
                case R.id.btn_create_playlist:
                    break;
                case R.id.btn_select_all:
                    break;
            }
        }
    };

//    public void bindData(String type) {
//        switch (type) {
//            case "tv":
////                tvMediaMap = BaseApplication.getTVMediaMap();
////                tv_video_list.clear();
////                tv_audio_list.clear();
////                tv_image_list.clear();
////                tv_video_list.addAll(tvMediaMap.get("video"));
////                tv_audio_list.addAll(tvMediaMap.get("audio"));
////                tv_image_list.addAll(tvMediaMap.get("image"));
////                tv_video_adapter.notifyDataSetChanged();
////                tv_audio_adapter.notifyDataSetChanged();
////                tv_image_adapter.notifyDataSetChanged();
//                break;
//            case "pc":
////                pcMediaMap = BaseApplication.getPCMediaMap();
////                pc_video_list.clear();
////                pc_audio_list.clear();
////                pc_image_list.clear();
////                pc_video_list.addAll(pcMediaMap.get("video"));
////                pc_audio_list.addAll(pcMediaMap.get("audio"));
////                pc_image_list.addAll(pcMediaMap.get("image"));
////                pc_video_adapter.notifyDataSetChanged();
////                pc_audio_adapter.notifyDataSetChanged();
////                pc_image_adapter.notifyDataSetChanged();
//                break;
//        }
//    }

//    public void expandListView(int id) {
//        ListView list2 = listManager[id];
//        ViewGroup.LayoutParams layoutParams2 = list2.getLayoutParams();
//        Boolean flag = (layoutParams2.height != ViewGroup.LayoutParams.MATCH_PARENT) ? true : false;
//        for (ListView list : listManager) {
//            ViewGroup.LayoutParams layoutParams = list.getLayoutParams();
//            layoutParams.height = 0;
//            list.setLayoutParams(layoutParams);
//        }
//        for (ImageButton btn : buttonManager) {
//            btn.setBackgroundResource(R.drawable.spread);
//        }
//        if (flag) {
//            layoutParams2.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            list2.setLayoutParams(layoutParams2);
//            buttonManager[id].setBackgroundResource(R.drawable.shrink);
//        }
//        else {
//            layoutParams2.height = 0;
//            list2.setLayoutParams(layoutParams2);
//            buttonManager[id].setBackgroundResource(R.drawable.spread);
//        }
//    }


//
//    View.OnClickListener clickListener1 = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case (R.id.btn_tv_video):
//                    expandListView(0);
//                    break;
//                case (R.id.btn_tv_audio):
//                    expandListView(1);
//                    break;
//                case (R.id.btn_tv_image):
//                    expandListView(2);
//                    break;
//                case (R.id.btn_pc_video):
//                    expandListView(3);
//                    break;
//                case (R.id.btn_pc_audio):
//                    expandListView(4);
//                    break;
//                case (R.id.btn_pc_image):
//                    expandListView(5);
//                    break;
//            }
//        }
//    };


    //点击某个媒体文件后，根据在tv和pc上的位置分别打开。
    //TODO:3.20上午
    ListView.OnItemClickListener itemClickListener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.e("onItemClick", "at begin");
            TextView mediaPathView = (TextView) view.findViewById(R.id.text_media_path);
            TextView mediaLocationView = (TextView) view.findViewById(R.id.text_media_location);
            TextView mediaType = (TextView) view.findViewById(R.id.text_media_type);
            TextView mediaIsFolder = (TextView) view.findViewById(R.id.text_media_isfolder);
            switch (mediaLocationView.getText().toString()) {
                case ("tv"):
                    if (!mediaIsFolder.getText().toString().equals("true")) {
                        String cmd = JSON.toJSONString(CommandUtil.createOpenTvMediaCommand(mediaPathView.getText().toString()));
                        new Send2TVThread(cmd, TV_IP, TV_OUT_PORT).start();
                    } else {
                        BaseApplication.requestDepthChange = ADD_DEPTH;
                        getRemoteFolder("tv",mediaType.getText().toString(),mediaPathView.getText().toString(),false);
                    }
                    break;
                case ("mobile"):
                    BaseApplication.requestDepthChange = ADD_DEPTH;
                    getRemoteFolder("pc",mediaType.getText().toString(),mediaPathView.getText().toString(),true);
                    break;
                case ("pc"):
                    if (!mediaIsFolder.getText().toString().equals("true")) {
                        OpenMethodUtil.showDialog("media", mediaPathView.getText().toString(), mContext);
                    } else {
                        BaseApplication.requestDepthChange = ADD_DEPTH;
                        getRemoteFolder("pc",mediaType.getText().toString(),mediaPathView.getText().toString(),false);
                    }
                    break;
            }
        }
    };


    //location为目标location，不是当前的location
    public void getRemoteFolder(String location, String type, String path,Boolean root) {
        String cmd;
        BaseApplication.requestPath = path;
        BaseApplication.requestType = type;
        BaseApplication.requestLocation = location;
        switch (location) {
            case "tv":
                if (root) {
                    cmd = JSON.toJSONString(CommandUtil.createOpenTvPathCommand(path,type,true));
                } else {
                    cmd = JSON.toJSONString(CommandUtil.createOpenTvPathCommand(path,type,false));
                }
                new GetTvFolderThread(cmd, myHandler).start();
                break;
            case "pc":
                if (root) {
                    cmd = JSON.toJSONString(CommandUtil.createOpenPcPathCommand(path,type,true));
                    Log.e("folder cmd to pc : ",cmd);
                } else {
                    cmd = JSON.toJSONString(CommandUtil.createOpenPcPathCommand(path,type,false));
                }
                new GetPcFolderThread(cmd,myHandler).start();
                break;
        }
    }

    ListView.OnItemLongClickListener itemLongClickListener1 = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
        }
    };




    public class MediaAdapter extends MyAdapter<MediaItem>{
        public MediaAdapter(ArrayList<MediaItem> media_list) {
            super(media_list,R.layout.media_item_layout_0201);
        }
        @Override
        public void bindView(ViewHolder holder, MediaItem obj) {
//            String filename = AppUtil.url2imgname(obj.getThumbnailurl());
//            File f = new File(filename);
//            //TODO:使用setImageURI可能会出问题。
//            if (f.exists()) {
//                Uri uri = Uri.fromFile(f);
//                holder.setImageWithUri(R.id.img_thumbnail,uri);
//            }else {
//                holder.setImage(R.id.img_thumbnail, obj.getThumbnailurl(), mContext);
//            }
            //TODO:先不做缓存；Glide貌似会自动缓存。
//            if (!obj.getIsFolder() && !obj.getType().equals("audio")) {
//                Log.e("getThumbnail", obj.getThumbnailurl());
//            }
            if (obj.getIsFolder()) {
                holder.setImageResource(R.id.img_thumbnail, R.drawable.folder);
            }else{
                switch (obj.getType()) {
                    case "video":
                    case "image":
                        holder.setImage(R.id.img_thumbnail, obj.getThumbnailurl(), mContext);
                        break;
                    case "audio":
                        holder.setImageResource(R.id.img_thumbnail, R.drawable.music);
                        break;
                }
            }
            holder.setText(R.id.text_media_name, obj.getName());
            holder.setText(R.id.text_media_path, obj.getPathName());
            holder.setText(R.id.text_media_location, obj.getLocation());
            holder.setText(R.id.text_media_type, obj.getType());
            holder.setText(R.id.text_media_isfolder, obj.getIsFolder().toString());
        }
    }

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case GET_TV_MEDIA_OK:
//                    bindData("tv");
//                    Toast.makeText(mContext, "获取TV媒体成功！",Toast.LENGTH_SHORT).show();
//                    break;
//                case GET_PC_MEDIA_OK:
//                    bindData("pc");
//                    Toast.makeText(mContext, "获取PC媒体成功！",Toast.LENGTH_SHORT).show();
//                    break;
//                case GET_TV_MEDIA_FAIL:
//                    Toast.makeText(mContext, "获取TV媒体失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
//                    break;
//                case GET_PC_MEDIA_FAIL:
//                    Toast.makeText(mContext, "获取PC媒体失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
//                    break;
                case GET_TV_FOLDER_OK:
                case GET_PC_FOLDER_OK:
                    mediaList.clear();
                    mediaList.addAll(BaseApplication.currentMediaList);
                    mediaAdapter.notifyDataSetChanged();


                    currentLocation = BaseApplication.requestLocation;
                    currentType = BaseApplication.requestType;
                    switch (BaseApplication.requestDepthChange) {
                        case ADD_DEPTH:
                            currentDepth++;
                            pathStack.push(currentPath);
                            break;
                        case DELETE_DEPTH:
                            currentDepth--;
                            pathStack.pop();
                            break;
                        case JUMP_2:
                            currentDepth = 2;
                            pathStack.empty();
                            pathStack.push("/");
                            break;
                        case JUMP_1:
                            currentDepth = 1;
                            pathStack.empty();
                            break;
                    }
                    currentPath = BaseApplication.requestPath;

                    if (currentDepth > 1) {
                        btn_return.setVisibility(View.VISIBLE);
                        btn_show_pc.setVisibility(View.VISIBLE);
                        btn_show_tv.setVisibility(View.VISIBLE);
                    }


                    break;
                case GET_TV_FOLDER_FAIL:
                    Log.e("hander", "GET_TV_FOLDER_FAIL");
                    break;
                case GET_PC_FOLDER_FAIL:
                    Log.e("hander", "GET_PC_FOLDER_FAIL");
                    break;

                //TODO:3.20上午
                case GET_DLNA_LIST_OK:
                    Log.e("handler","GET_DLNA_LIST_OK");
                    break;

            }
        }
    };






}

