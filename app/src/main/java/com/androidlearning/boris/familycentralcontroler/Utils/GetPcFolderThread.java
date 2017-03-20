package com.androidlearning.boris.familycentralcontroler.Utils;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.AppItem;
import com.androidlearning.boris.familycentralcontroler.Model.DeviceItem;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;
import com.androidlearning.boris.familycentralcontroler.Model.PCCommandItem;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_APP_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_APP_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_DEVICE_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_FOLDER_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_FOLDER_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_MEDIA_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_PC_MEDIA_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_FOLDER_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.PC_IP;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.PC_OUT_PORT;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.SOCKET_TIMEOUT;

/**
 * Created by poxiaoge on 2017/3/10.
 */

public class GetPcFolderThread extends Thread {

    private final String tag =this.getClass().getSimpleName();

    private String cmdString;
    private Handler handler;

    public GetPcFolderThread(String cmdString, Handler handler) {
        this.cmdString = cmdString;
        this.handler = handler;
    }

    @Override
    public void run() {
        String cmd = cmdString;
        Log.e(tag, "Begin run");
        Socket client = new Socket();
        DataOutputStream out;
        DataInputStream in;
        String data;
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        ByteArrayInputStream inBytes = new ByteArrayInputStream(cmd.getBytes());
        try {
            Log.e(tag, "Before client");
//            client = new Socket(PC_IP, PC_OUT_PORT);
            client.connect(new InetSocketAddress(PC_IP, PC_OUT_PORT), SOCKET_TIMEOUT);
            Log.e(tag, "after client");
//            in = new DataInputStream(client.getInputStream());
//            out = new DataOutputStream(client.getOutputStream());
//            out.writeUTF(cmd);
//            out.flush();
            Log.e(tag, "get pc folder cmd 2 pc is: " + cmd);
            IOUtils.write(cmd, client.getOutputStream(), "UTF-8");
//            IOUtils.copy(inBytes, client.getOutputStream(), 8192);
            Log.e(tag, "Out success!");
//            data = in.readUTF();
            Log.e(tag, "before get input");
            IOUtils.copy(client.getInputStream(), outBytes, 8192);
            data = new String(outBytes.toByteArray(), "UTF-8");


            Log.e(tag, "In success!");
            Log.e(tag, "data from pc is: " + data);

//            switch (type) {
//                case ("MEDIALIST"):
//                    BaseApplication.setPCMediaMap(JSON.parseObject(data, new TypeReference<Map<String, List<MediaItem>>>() {
//                    }));
//                    myhandler.sendEmptyMessage(GET_PC_MEDIA_OK);
//                    break;
//                case ("APPLIST"):
//                    BaseApplication.setPCAppList(JSON.parseObject(data, new TypeReference<List<AppItem>>() {
//                    }));
//                    myhandler.sendEmptyMessage(GET_PC_APP_OK);
//                    break;
//                case ("DEVICELIST"):
//                    BaseApplication.setDeviceList(JSON.parseObject(data, new TypeReference<List<DeviceItem>>() {
//                    }));
//                    myhandler.sendEmptyMessage(GET_PC_DEVICE_OK);
//                    break;
//            }
            BaseApplication.currentMediaList.clear();
            BaseApplication.currentMediaList.addAll(JSON.parseObject(data, new TypeReference<List<MediaItem>>() {
            }));
            handler.sendEmptyMessage(GET_PC_FOLDER_OK);


//            BaseApplication.setTVAppList(JSON.parseObject(data, new TypeReference<List<AppItem>>(){}));
//            out.close();
//            client.close();
//            in.close();


        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(GET_PC_FOLDER_FAIL);
        } finally {
            if (!client.isClosed()) {
                IOUtils.closeQuietly(client);
            }
        }

    }





}
