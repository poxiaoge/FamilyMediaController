package com.androidlearning.boris.familycentralcontroler.Utils;

import com.androidlearning.boris.familycentralcontroler.Model.CommandItem;
import com.androidlearning.boris.familycentralcontroler.Model.PCCommandItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poxiaoge on 2016/12/24.
 */

public class CommandUtil {

    public static CommandItem createGetTvAppCommand(){
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("GET_TV_APPS");
        cmd.setSecondcommand("");
        return cmd;
    }
    public static CommandItem createOpenTvAppCommand(String appPackage){
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("OPEN_TV_APPS");
        cmd.setSecondcommand(appPackage);
        return cmd;
    }

//    public static CommandItem createGetTvVideoCommand(){
//        CommandItem cmd = new CommandItem();
//        cmd.setFirstcommand("GET_TV_VIDEOS");
//        cmd.setSecondcommand("");
//        return_pic cmd;
//    }
//    public static CommandItem createGetTvAudioCommand(){
//        CommandItem cmd = new CommandItem();
//        cmd.setFirstcommand("GET_TV_AUDIOS");
//        cmd.setSecondcommand("");
//        return_pic cmd;
//    }
//    public static CommandItem createGetTvImageCommand(){
//        CommandItem cmd = new CommandItem();
//        cmd.setFirstcommand("GET_TV_IMAGES");
//        cmd.setSecondcommand("");
//        return_pic cmd;
//    }
    public static CommandItem createGetTvMediaCommand(){
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("GET_TV_MEDIAS");
        cmd.setSecondcommand("");
        return cmd;
    }
    public static CommandItem createOpenTvMediaCommand(String mediaPath){
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("OPEN_TV_MEDIAS");
        cmd.setSecondcommand(mediaPath);
        return cmd;
    }

    public static CommandItem createOpenTvRdp() {
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("OPEN_RDP");
        cmd.setSecondcommand("");
        return cmd;
    }


    public static CommandItem createOpenTvMiracast() {
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand("OPEN_MIRACAST");
        cmd.setSecondcommand("");
        return cmd;
    }



    public static PCCommandItem createGetPcListCommand(String listName) {
        PCCommandItem cmd = new PCCommandItem();
        cmd.setName(listName);
        cmd.setCommand("");
        Map<String, String> params = new HashMap<>();
        cmd.param = params;
        return cmd;
    }


    public static PCCommandItem createOpenPcMiracastCommand(String screen,String name,String path){
        PCCommandItem cmd = new PCCommandItem();
        cmd.setName("MIRACAST");
        cmd.setCommand("PLAY");
        Map<String, String> params = new HashMap<>();
        params.put("screen", screen);
        params.put("name", name);
        params.put("path", path);
        cmd.param = params;
        return cmd;
    }

    public static PCCommandItem createOpenPcDlnaMediaCommand(String path, String render) {
        PCCommandItem cmd = new PCCommandItem();
        cmd.setName("DLNA");
        cmd.setCommand("PLAY");
        Map<String, String> params = new HashMap<>();
        params.put("path", path);
        params.put("render", render);
        cmd.param = params;
        return cmd;
    }


    public static PCCommandItem createOpenPcAppMediaCommand(String name,String path) {
        PCCommandItem cmd = new PCCommandItem();
        cmd.setName("RDP");
        cmd.setCommand("PLAY");
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("path", path);
        cmd.param = params;
        return cmd;

    }

    //TODO:createOpenPcPathCommand及tv需要更改
    public static PCCommandItem createOpenPcPathCommand(String path, String mime,Boolean root) {
        PCCommandItem cmd = new PCCommandItem();
        cmd.setName("MEDIALIST");
        cmd.setCommand("GET");
        Map<String, String> params = new HashMap<>();
        params.put("type", mime);
        if (root) {
            params.put("folder", "root");
        }else {
            params.put("folder", path);
        }
        cmd.param = params;
        return cmd;
    };


    public static CommandItem createOpenTvPathCommand(String path,String mime,Boolean root) {
        CommandItem cmd = new CommandItem();
        cmd.setFirstcommand(mime);
        cmd.setSecondcommand(path);
        cmd.setThirdcommand(root);
        return cmd;
    }







}
