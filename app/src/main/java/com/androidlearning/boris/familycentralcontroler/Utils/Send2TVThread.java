package com.androidlearning.boris.familycentralcontroler.Utils;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.androidlearning.boris.familycentralcontroler.Application.BaseApplication.SOCKET_TIMEOUT;


/**
 * Created by poxiaoge on 2016/12/23.
 */

public class Send2TVThread extends Thread{
    private String cmd;
    private String ip;
    private int port;
    private final String tag = "Send2TVThread";

    public Send2TVThread(String cmd, String ip, int port){
        this.cmd = cmd;
        this.ip = ip;
        this.port =port;
    }

    @Override
    public void run() {
        Log.e(tag, "Begin run");
        Socket client = new Socket();
        DataOutputStream out = null;
//        DataInputStream in;
        String data;
        try {
            client.connect(new InetSocketAddress(ip, port), SOCKET_TIMEOUT);
            out = new DataOutputStream(client.getOutputStream());
////            in = new DataInputStream(client.getInputStream());
            Log.e(tag, "before out");
            out.writeUTF(cmd);
            out.flush();
//            IOUtils.write(cmd, client.getOutputStream(), "UTF-8");
            Log.e(tag, "Out success!");
//            out.close();
        } catch (IOException e) {
            e.printStackTrace();
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
