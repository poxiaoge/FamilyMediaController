package com.androidlearning.boris.familycentralcontroler.Utils;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.AppItem;
import com.androidlearning.boris.familycentralcontroler.Model.CommandItem;
import com.androidlearning.boris.familycentralcontroler.Model.MediaItem;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_APP_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_APP_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_MEDIA_FAIL;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.GET_TV_MEDIA_OK;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.SOCKET_TIMEOUT;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_IP;
import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.TV_OUT_PORT;


/**
 * Created by poxiaoge on 2017/1/5.
 */

public class GetTvListThread extends Thread {

    private final String tag =this.getClass().getSimpleName();

    String type;
    Handler handler;

    public GetTvListThread(String type, Handler handler) {
        this.type = type;
        this.handler = handler;
    }

    @Override
    public void run() {
        CommandItem cmdItem = type.equals("app") ? CommandUtil.createGetTvAppCommand() : CommandUtil.createGetTvMediaCommand();
        String cmd = JSON.toJSONString(cmdItem);
        Log.e("cmd2tv", cmd);
        Socket client = new Socket();
        DataOutputStream out = null;
        String data;
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        try {
            Log.e(tag, "Before client");
//            client = new Socket(TV_IP, TV_OUT_PORT);
            client.connect(new InetSocketAddress(TV_IP, TV_OUT_PORT), SOCKET_TIMEOUT);
            Log.e(tag, "after client");
            out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(cmd);
            out.flush();
            Log.e(tag, "Out success!");
            IOUtils.copy(client.getInputStream(), outBytes,8192);
            data = new String(outBytes.toByteArray(), "UTF-8");
            Log.e(tag, "In success!");
            Log.e(tag, data);
            if (type.equals("app")) {
                BaseApplication.setTVAppList(JSON.parseObject(data, new TypeReference<List<AppItem>>(){}));
                handler.sendEmptyMessage(GET_TV_APP_OK);
            } else if (type.equals("media")) {
                BaseApplication.setTVMediaMap(JSON.parseObject(data, new TypeReference<Map<String,List<MediaItem>>>() {}));
                handler.sendEmptyMessage(GET_TV_MEDIA_OK);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (type.equals("app")) {
                handler.sendEmptyMessage(GET_TV_APP_FAIL);
            } else if (type.equals("media")) {
                handler.sendEmptyMessage(GET_TV_MEDIA_FAIL);
            }
        }
        finally {
            if (out != null) {
                IOUtils.closeQuietly(out);
            }
            if (!client.isClosed()) {
                IOUtils.closeQuietly(client);
            }
        }

    }
}
