package com.androidlearning.boris.familycentralcontroler;


import android.os.Handler;
import android.os.Message;

import com.androidlearning.boris.familycentralcontroler.fragment01.computerFragment01;
import com.androidlearning.boris.familycentralcontroler.fragment01.computerFragment02;
import com.androidlearning.boris.familycentralcontroler.fragment01.computerMonitorActivity;
import com.androidlearning.boris.familycentralcontroler.fragment01.page01Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment01.prepareDataForFragment;
import com.androidlearning.boris.familycentralcontroler.fragment02.page02Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment03.page03Fragment;
import com.androidlearning.boris.familycentralcontroler.fragment04.page04Fragment;
import com.androidlearning.boris.familycentralcontroler.jsonFormat.ProcessFormat;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by boris on 2016/12/12.
 */

public class MyHandler extends Handler {
    page01Fragment fragment01;
    page02Fragment fragment02;
    page03Fragment fragment03;
    page04Fragment fragment04;
    computerFragment01 computerFragment01;
    computerFragment02 computerFragment02;
    computerMonitorActivity activity;

    public MyHandler() {}

    MyHandler(page01Fragment fragment) {
        this.fragment01 = new WeakReference<>(fragment).get();
    }

    MyHandler(page02Fragment fragment) {
        this.fragment02 = new WeakReference<>(fragment).get();
    }

    MyHandler(page03Fragment fragment) {
        this.fragment03 = new WeakReference<>(fragment).get();
    }

    MyHandler(page04Fragment fragment) {
        this.fragment04 = new WeakReference<>(fragment).get();
    }

    public MyHandler(computerFragment01 monitorTab01) {
        this.computerFragment01 = monitorTab01;
    }

    public MyHandler(computerFragment02 monitorTab02) {
        this.computerFragment02 = monitorTab02;
    }

    public MyHandler(computerMonitorActivity computerMonitorActivity) {
        this.activity = computerMonitorActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Order.refreshTab01MiddleImage_order:
                fragment01.refreshMiddle();
                break;
            case Order.refreshTab01ComputerFragment01_order: {
                String memoryPercent      = prepareDataForFragment.getMemoryPercentData() + "";
                String memoryUsed         = prepareDataForFragment.getMemory_used() + "";
                String memoryTotal        = prepareDataForFragment.getMemory_total() + "";
                String cpuPercent         = prepareDataForFragment.getCpuPercentData() + "";
                int    memoryPercentValue = prepareDataForFragment.getMemoryPercentData();
                int[]  memoryPercentArray = prepareDataForFragment.getMemoryPercentArray();
                int[]  cpuPercentArray    = prepareDataForFragment.getCpuPercentArray();
                computerFragment01.refreshViews(memoryPercent, memoryUsed, memoryTotal, cpuPercent,
                        memoryPercentValue, memoryPercentArray, cpuPercentArray);
            }
                break;
            case Order.refreshTab01ComputerActivity_order: {
                String memoryPercent = prepareDataForFragment.getMemoryPercentData() + "";
                String memory = prepareDataForFragment.getMemory_used() + "";
                String netDl = prepareDataForFragment.getNet_down() + "";
                String netUl = prepareDataForFragment.getNet_up() + "";
                activity.refreshNetAndMemoryText(memoryPercent, memory, netDl, netUl);
            }
                break;
            case Order.refreshTab01ComputerFragment02_order: {
                List<ProcessFormat> increasedProcesses = prepareDataForFragment.getIncreasedProcesses();
                List<Integer> decreasedProcessesID = prepareDataForFragment.getDecreasedProcesses();
                computerFragment02.refreshItems(increasedProcesses, decreasedProcessesID);
            }
            break;
        }
    }

}
