package com.CC.client.tools;

import jdk.nashorn.internal.ir.CallNode;

import java.util.HashMap;

/**
 * @author wqy
 * @version 1.0
 * 管理通讯客户端用于服务器保持通信的线程
 */
public class ManageClientConServerThread {
    private static HashMap hashMap= new HashMap<String,ClientConServerThread>();
    //把创建好的线程放入到HashMap中去
    public static void addClientConServerThread(String id,ClientConServerThread ccst){
        hashMap.put(id,ccst);
    }
    //可以通过id来取得该线程
    public static ClientConServerThread getClientConServerThread(String id){
        return (ClientConServerThread)hashMap.get(id);
    }
}
