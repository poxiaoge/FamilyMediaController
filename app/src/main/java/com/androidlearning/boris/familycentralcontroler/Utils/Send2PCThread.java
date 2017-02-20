package com.androidlearning.boris.familycentralcontroler.Utils;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.SOCKET_TIMEOUT;


/**
 * Created by poxiaoge on 2017/1/3.
 */

public class Send2PCThread extends Thread {
    private String cmd;
    private String ip;
    private int port;
    private final String tag = "Send2PCThread";

    public Send2PCThread(String cmd, String ip, int port){
        this.cmd = cmd;
        this.ip = ip;
        this.port =port;
    }

    @Override
    public void run() {
        Log.e(tag, "Begin run");
        Socket client = new Socket();
        String data;
        try {
            client.connect(new InetSocketAddress(ip, port), SOCKET_TIMEOUT);
            Log.e(tag, "before out");
            IOUtils.write(cmd, client.getOutputStream(), "UTF-8");
            Log.e(tag, "Out success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(client);
        }

    }





}
