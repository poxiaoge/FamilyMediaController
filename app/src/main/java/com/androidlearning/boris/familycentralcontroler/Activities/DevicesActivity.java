package com.androidlearning.boris.familycentralcontroler.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Adapters.MyAdapter;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.DeviceItem;
import com.androidlearning.boris.familycentralcontroler.R;
import com.androidlearning.boris.familycentralcontroler.Utils.GetPcListThread;

import java.util.ArrayList;
import java.util.List;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_OK;


public class DevicesActivity extends AppCompatActivity {
    private final String tag = this.getClass().getSimpleName();


    ListView listDevice;
    Button btn_update_devices;
    BaseAdapter deviceAdapter;
    List<DeviceItem> deviceList;

    private Context mContext;
    private Intent j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        mContext=this;
        initView();
        j = getIntent();
    }

    public void initView(){
        deviceList = new ArrayList<>();
        if (BaseApplication.getDeviceOK) {
            deviceList.addAll(BaseApplication.getDeviceList());
        }

        listDevice = (ListView) findViewById(R.id.list_devices);
        deviceAdapter = new MyAdapter<DeviceItem>((ArrayList) deviceList,R.layout.device_item_layout) {
            @Override
            public void bindView(ViewHolder holder, DeviceItem obj) {
                holder.setTag(R.id.text_device_name, obj);
                holder.setText(R.id.text_device_name, obj.getDeviceName());
                holder.setText(R.id.text_device_type, obj.getType());
                holder.setText(R.id.text_device_ip, obj.getIp());
                if (obj.getDlnaOk()) {
                    holder.setVisibility(R.id.text_device_dlnaOk, View.VISIBLE);
                }
                if (obj.getMiracastOk()) {
                    holder.setVisibility(R.id.text_device_miracastOk, View.VISIBLE);
                }
                if (obj.getRdpOk()) {
                    holder.setVisibility(R.id.text_device_rdpOk, View.VISIBLE);
                }
            }
        };
        listDevice.setAdapter(deviceAdapter);
        listDevice.setOnItemClickListener(itemClickListener);

        btn_update_devices = (Button) findViewById(R.id.btn_update_devices);
        btn_update_devices.setOnClickListener(clickListener1);
    }

    ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView deviceName = (TextView) view.findViewById(R.id.text_device_name);
            DeviceItem item = (DeviceItem) deviceName.getTag();
            Log.e(tag, JSON.toJSONString(item));
            Bundle b = new Bundle();
            b.putSerializable("device",item);
            j.putExtras(b);
            setResult(999, j);
            finish();
        }
    };

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"正在获取屏幕列表……",Toast.LENGTH_SHORT).show();
            BaseApplication.getDeviceOK = false;
            new GetPcListThread("DEVICELIST",myHandler).start();
        }
    };

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_PC_DEVICE_OK:
                    Toast.makeText(mContext, "获取屏幕列表成功！",Toast.LENGTH_SHORT).show();
                    deviceList.clear();
                    deviceList.addAll(BaseApplication.getDeviceList());
                    deviceAdapter.notifyDataSetChanged();
                    break;
                case GET_PC_DEVICE_FAIL:
                    Toast.makeText(mContext, "获取屏幕列表失败，请稍后重新获取！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



}
