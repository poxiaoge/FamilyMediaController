package com.androidlearning.boris.familycentralcontroler.fragment03;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Adapters.MyAdapter;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.AppItem;
import com.androidlearning.boris.familycentralcontroler.R;
import com.androidlearning.boris.familycentralcontroler.Utils.CommandUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.GetTvListThread;
import com.androidlearning.boris.familycentralcontroler.Utils.OpenMethodUtil;
import com.androidlearning.boris.familycentralcontroler.Utils.Send2TVThread;

import java.util.ArrayList;
import java.util.List;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_APP_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_APP_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_APP_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_APP_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_IP;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_OUT_PORT;

/**
 * Created by boris on 2016/11/29.
 * TAB03---应用的Fragment
 */

public class page03Fragment extends Fragment {

//    private GridView id_tab03_applicationComputer;
//    private GridView id_tab03_applicationScreen;
//    private SimpleAdapter adapter01;
//    private SimpleAdapter adapter02;
//    private static List<Map<String, Object>> data_list01;
//    private static List<Map<String, Object>> data_list02;
//    private int[] icons01 = new int[] {
//            R.drawable.life_icon_chongzhi, R.drawable.life_icon_dianying, R.drawable.life_icon_haiwaigou, R.drawable.life_icon_huangkuan,
//            R.drawable.life_icon_nuomi,    R.drawable.life_icon_qianzheng, R.drawable.life_icon_shoukuan, R.drawable.life_icon_uber,
//            R.drawable.life_icon_xinyongka, R.drawable.life_icon_waimai};
//    private String[] names01 = new String[] {
//            "手机充值", "电影", "海外购", "信用卡还款",
//            "百度糯米", "签证", "收款",   "快车",
//            "信用卡", "外卖"};
//    private int[] icons02 = new int[] {
//            R.drawable.life_icon_chongzhi, R.drawable.life_icon_dianying, R.drawable.life_icon_haiwaigou, R.drawable.life_icon_huangkuan,
//            R.drawable.life_icon_nuomi,    R.drawable.life_icon_qianzheng, R.drawable.life_icon_shoukuan, R.drawable.life_icon_uber,
//            R.drawable.life_icon_xinyongka, R.drawable.life_icon_waimai};
//    private String[] names02 = new String[] {
//            "手机充值", "电影", "海外购", "信用卡还款",
//            "百度糯米", "签证", "收款",   "快车",
//            "信用卡", "外卖"};
    View rootView;
    Context mContext;
    GridView grid_tv_apps;
    GridView grid_pc_apps;
    MyAdapter<AppItem> tv_app_adapter = null;
    MyAdapter<AppItem> pc_app_adapter = null;
    List<AppItem> tv_app_list = new ArrayList<>();
    List<AppItem> pc_app_list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 为每个list填充数据
//        data_list01 = new ArrayList<>();
//        data_list02 = new ArrayList<>();
//        getData();
    }

//    private void getData() {
//        for(int i = 0; i < icons01.length; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("icon", icons01[i]);
//            map.put("name", names01[i]);
//            data_list01.add(map);
//        }
//        for(int i = 0; i < icons02.length; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("icon", icons02[i]);
//            map.put("name", names02[i]);
//            data_list02.add(map);
//        }
//
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab03, container, false);
        rootView = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        new GetTvListThread("app",myHandler).start();
        new GetPcListThread("APPLIST",myHandler).start();
        initViews();
//        initEvents();
    }

//    private void initEvents() {
//        id_tab03_applicationComputer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast toast = Toast.makeText(getActivity(), names01[position], Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//        id_tab03_applicationScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast toast = Toast.makeText(getActivity(), names01[position], Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//    }

    private void initViews() {
//        id_tab03_applicationComputer = (GridView) getActivity().findViewById(R.id.id_tab03_applicationComputer);
//        id_tab03_applicationScreen   = (GridView) getActivity().findViewById(R.id.id_tab03_applicationScreen);
//        String[] from = {"icon", "name"};
//        int[]    to   = {R.id.image, R.id.text};
//        adapter01 = new SimpleAdapter(getActivity(), data_list01, R.layout.item03, from, to);
//        adapter02 = new SimpleAdapter(getActivity(), data_list02, R.layout.item03, from, to);
//        id_tab03_applicationComputer.setAdapter(adapter01);
//        id_tab03_applicationScreen.setAdapter(adapter02);
//        id_tab03_applicationComputer.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        id_tab03_applicationScreen.setSelector(new ColorDrawable(Color.TRANSPARENT));

        grid_pc_apps = (GridView) rootView.findViewById(R.id.grid_pc_apps);
        grid_tv_apps = (GridView) rootView.findViewById(R.id.grid_tv_apps);

        tv_app_adapter = new MyAdapter<AppItem>((ArrayList) tv_app_list, R.layout.app_item_layout) {
            @Override
            public void bindView(ViewHolder holder, AppItem obj) {
                holder.setText(R.id.text_app, obj.getAppName());
                holder.setText(R.id.text_package, obj.getPackageName());
                holder.setImageResource(R.id.image_app, R.drawable.life_icon_chongzhi);
            }
        };
        pc_app_adapter = new MyAdapter<AppItem>((ArrayList) pc_app_list, R.layout.app_item_layout) {
            @Override
            public void bindView(ViewHolder holder, AppItem obj) {
                holder.setText(R.id.text_app, obj.getAppName());
                holder.setText(R.id.text_package, obj.getPackageName());
                holder.setImageResource(R.id.image_app, R.drawable.life_icon_chongzhi);
            }
        };
        grid_pc_apps.setAdapter(pc_app_adapter);
        grid_tv_apps.setAdapter(tv_app_adapter);
        grid_pc_apps.setOnItemClickListener(pcItemClickListener);
        grid_tv_apps.setOnItemClickListener(tvItemClickListener);
    }

    GridView.OnItemClickListener tvItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView packageNameView = (TextView) view.findViewById(R.id.text_package);
            String cmd = JSON.toJSONString(CommandUtil.createOpenTvAppCommand(packageNameView.getText().toString()));
            new Send2TVThread(cmd, TV_IP, TV_OUT_PORT).start();
        }
    };
    GridView.OnItemClickListener pcItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView packageNameView = (TextView) view.findViewById(R.id.text_package);
            OpenMethodUtil.showDialog("application",packageNameView.getText().toString(),mContext);
        }
    };




    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_TV_APP_OK:
                    tv_app_list.clear();
                    tv_app_list.addAll(BaseApplication.getTVAppList());
                    tv_app_adapter.notifyDataSetChanged();
                    Toast.makeText(mContext, "获取TV应用成功！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_PC_APP_OK:
                    pc_app_list.clear();
                    pc_app_list.addAll(BaseApplication.getPCAppList());
                    pc_app_adapter.notifyDataSetChanged();
                    Toast.makeText(mContext, "获取PC应用成功！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_TV_APP_FAIL:
                    Toast.makeText(mContext, "获取TV应用失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
                case GET_PC_APP_FAIL:
                    Toast.makeText(mContext, "获取PC应用失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };






}
