package com.androidlearning.boris.familycentralcontroler.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.androidlearning.boris.familycentralcontroler.Application.BaseApplication;
import com.androidlearning.boris.familycentralcontroler.Model.CommandItem;
import com.androidlearning.boris.familycentralcontroler.Model.PCCommandItem;
import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Created by poxiaoge on 2017/1/4.
 */

public class OpenMethodUtil {

    static String tag = "OpenMethodUtil";


    public static String[] appList = null;
    public static String[] mediaList = null;
    public static String[] dlnaList = null;


    public static void showDialog(final String name, final String path, final Context mContext) {
        final String [] methodList = name.equals("media") ? mediaList : appList;
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        alert = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("请选择打开的方式")
                .setItems(methodList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PCCommandItem cmdItem;
                        String cmd;
                        switch (methodList[which]) {
                            //TODO:3.20上午
                            case ("dlna"):
                                if (BaseApplication.dlnaOK) {
                                    cmdItem = CommandUtil.createOpenPcDlnaMediaCommand(path, BaseApplication.chosenDevice.getDeviceName());
                                    cmd = JSON.toJSONString(cmdItem);
                                    Log.e(tag, "cmd : " + cmd);
                                    new Send2PCThread(cmd,BaseApplication.PC_IP,BaseApplication.PC_OUT_PORT).start();
                                }else{
                                    if (BaseApplication.dlnaListOK) {
                                        showDlnaSelectDialog(path,mContext);
                                    }
                                }
                                break;
                            case ("miracast"):
                                cmdItem = CommandUtil.createOpenPcMiracastCommand(BaseApplication.chosenDevice.getScreen(), name, path);
                                cmd = JSON.toJSONString(cmdItem);
                                Log.e(tag, "cmd 1 : " + cmd);
                                CommandItem cmdItem2 = CommandUtil.createOpenTvMiracast();
                                String cmd2 = JSON.toJSONString(cmdItem2);
                                Log.e(tag, "cmd 2 : " + cmd2);
                                new Send2PCThread(cmd,BaseApplication.PC_IP,BaseApplication.PC_OUT_PORT).start();
                                new Send2TVThread(cmd2,BaseApplication.TV_IP,BaseApplication.TV_OUT_PORT).start();
                                break;
                            case ("rdp"):
                                CommandItem commandItem = CommandUtil.createOpenTvRdp();
                                cmd = JSON.toJSONString(commandItem);
                                Log.e(tag, "cmd 1 : " + cmd);
                                new Send2TVThread(cmd, BaseApplication.TV_IP, BaseApplication.TV_OUT_PORT).start();
                                PCCommandItem pcCommandItem = CommandUtil.createOpenPcAppMediaCommand(name, path);
                                String cmd3 = JSON.toJSONString(pcCommandItem);
                                Log.e(tag, "cmd 3 : " + cmd3);
                                new Send2PCThread(cmd3, BaseApplication.PC_IP, BaseApplication.PC_OUT_PORT).start();
                                break;
                        }
                    }
                }).create();
        alert.show();

    }

    //TODO:3.20上午
    public static void showDlnaSelectDialog(final String path, Context mContext) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        alert = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("请选择DLNA设备")
                .setItems(dlnaList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PCCommandItem cmdItem;
                        String cmd;
                        cmdItem = CommandUtil.createOpenPcDlnaMediaCommand(path, dlnaList[which]);
                        cmd = JSON.toJSONString(cmdItem);
                        Log.e(tag, "cmd : " + cmd);
                        new Send2PCThread(cmd,BaseApplication.PC_IP,BaseApplication.PC_OUT_PORT).start();
                    }
                }).create();
        alert.show();
    }





}
