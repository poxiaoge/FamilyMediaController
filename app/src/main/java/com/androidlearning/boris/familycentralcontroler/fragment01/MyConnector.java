package com.androidlearning.boris.familycentralcontroler.fragment01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by boris on 2016/12/16.
 *
 */

public class MyConnector {
    private static MyConnector instance = null;
    private Socket monitoringSocket = null;
    private BufferedWriter monitoringBufferedWriter = null;
    private BufferedReader monitoringBufferedReader = null;

    private String IP;
    private int monitorPort;
    private int actionPort;
    private boolean connetedState = false;

    private MyConnector() {}

    public static MyConnector getInstance() {
        if(instance == null) {
            synchronized (MyConnector.class) {
                if(instance == null) {
                    instance = new MyConnector();
                }
            }
        }
        return instance;
    }

    /**
     * <summary>
     *  创建监控信息的长连接，并初始化IP和执行操作(短连接)时使用的端口
     * </summary>
     * <param name="IP">IP地址</param>
     * <param name="monitorPort">监控(长连接)信息使用的端口</param>
     * <param name="actionPort">操作(短连接)信息使用的端口</param>
     * <returns>长连接创建状态</returns>
     */
    public boolean connect(String IP, int monitorPort, int actionPort) {
        this.IP = IP;
        this.monitorPort = monitorPort;
        this.actionPort = actionPort;

        if (monitoringSocket == null) {
            try {
                monitoringSocket = new Socket();
                monitoringSocket.connect(new InetSocketAddress(IP, monitorPort), 2000);
                monitoringBufferedWriter = new BufferedWriter(new OutputStreamWriter(monitoringSocket.getOutputStream(), "utf-8"));
                monitoringBufferedReader = new BufferedReader(new InputStreamReader(monitoringSocket.getInputStream(), "utf-8"));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * <summary>
     *  重新和服务器建立长连接(传输监控数据)
     * </summary>
     * <returns>重建状态</returns>
     */
    private boolean rebuildSocket()
    {
        try {
            monitoringSocket = new Socket();
            monitoringSocket.connect(new InetSocketAddress(IP, monitorPort), 2000);
            monitoringBufferedWriter = new BufferedWriter(new OutputStreamWriter(monitoringSocket.getOutputStream(), "utf-8"));
            monitoringBufferedReader = new BufferedReader(new InputStreamReader(monitoringSocket.getInputStream(), "utf-8"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * <summary>
     *  获取当前和服务器的连接状态
     * </summary>
     * <returns>和服务器的连接状态</returns>
     */
    public boolean getConnetedState() {
        return connetedState;
    }

    /**
     * <summary>
     *  使用短连接发送操作指令，并获取执行结果
     * </summary>
     * <param name="msgSend">待发送的命令</param>
     * <returns>执行结果</returns>
     */
    public String getActionStateMsg(String msgSend) {
        String msg = "";
        int len;

        try {
            Socket actionSocket = new Socket(IP, actionPort);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(actionSocket.getOutputStream(), "utf-8"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(actionSocket.getInputStream(), "utf-8"));
            bufferedWriter.write(msgSend);
            bufferedWriter.flush();

            char[] rev = new char[4000];
            if((len = bufferedReader.read(rev)) > 0) {
                msg = new String(rev, 0, len);
            }
            if(!(msg.contains("}"))) {
                if((len = monitoringBufferedReader.read(rev)) > 0) {
                    msg += new String(rev, 0, len);
                }
            }
            actionSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if((msg.contains("{\"status\"")) && (msg.contains("}"))) {
            return msg;
        } else {
            return "";
        }
    }



    /**
     * <summary>
     *  发送指定信息
     * </summary>
     * <param name="msg">消息</param>
     * <returns></returns>
     */
    public boolean sentMonitorCommand(String msg) {
        boolean result;
        if(monitoringBufferedWriter != null) {
            try {
                monitoringBufferedWriter.write(msg);
                monitoringBufferedWriter.flush();
                result = true;
                connetedState = true;
            } catch (IOException e) {
                boolean signal = rebuildSocket();
                if(signal) {
                    System.out.println("重连成功");
                    sentMonitorCommand(msg);
                    result = true;
                    connetedState = true;
                } else {
                    result = false;
                    connetedState = false;
                    System.out.println("重连失败");
                }
            }
        }
        else {
            boolean signal = rebuildSocket();
            if(signal) {
                System.out.println("重连成功");
                sentMonitorCommand(msg);
                result = true;
                connetedState = true;
            } else {
                result = false;
                connetedState = false;
                System.out.println("重连失败");
            }
        }
        return result;
    }

    /**
     * <summary>
     *  返回获取到的消息(格式1: 完整的json字符串 格式2: 空字符)
     * </summary>
     * <returns>json串或空字符串</returns>
     */
    public String getMonitorMsg() {
        String msg = "";
        int len;
        if(monitoringBufferedReader != null) {
            char[] rev = new char[8000];
            try {
                if((len = monitoringBufferedReader.read(rev)) > 0) {
                    msg = new String(rev, 0, len);
                }
                if(!(msg.contains("}}"))) {
                    if((len = monitoringBufferedReader.read(rev)) > 0) {
                        msg += new String(rev, 0, len);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if((msg.contains("{\"status\"")) && (msg.contains("}}"))) {
            return msg;
        } else {
            return "";
        }
    }

    /**
     * <summary>
     *  重置参数
     * </summary>
     */
    public void reSetParams() {
        instance = null;
        if(monitoringSocket != null) {
            try {
                monitoringSocket.close();
                System.out.println("monitoringSocket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(monitoringBufferedReader != null) {
            try {
                monitoringBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(monitoringBufferedWriter != null) {
            try {
                monitoringBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
