package com.androidlearning.boris.familycentralcontroler.Model;

/**
 * Created by poxiaoge on 2016/12/23.
 */

public class CommandItem {

//    private String name;
//    private String command;
//    private Map<String,String> param;
    private String firstcommand;
    private String secondcommand;

    public void setFirstcommand(String firstcommand) {
        this.firstcommand = firstcommand;
    }

    public String getFirstcommand() {
        return firstcommand;
    }

    public void setSecondcommand(String secondcommand) {
        this.secondcommand = secondcommand;
    }

    public String getSecondcommand() {
        return secondcommand;
    }

}
