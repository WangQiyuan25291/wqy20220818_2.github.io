package com.CC.server.model;

import jdk.nashorn.internal.ir.CallNode;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author wqy
 * @version 1.0
 */
public class ManageClientThread {
    public static HashMap hashMap= new HashMap<String,SerConClientThread>();
    //向hashMap中添加一个客户端进程
    public static void addClientThread(String uid, SerConClientThread ct){
        hashMap.put(uid,ct);
    }

    public static SerConClientThread getClient(String uid){
        return (SerConClientThread)hashMap.get(uid);
    }
    //返回当前在线的人的情况
    public static String getAllOnlineUserId(){
        //使用迭代器
        Iterator iterator = hashMap.keySet().iterator();
        String res="";
        while (iterator.hasNext()) {
            res+=iterator.next().toString()+" ";
        }
        return res;
    }
    //从集合中移除某个线程对象
    public static void removeSerConClientThread(String userId){
        hashMap.remove(userId);
    }
    public static HashMap<String,SerConClientThread> getHm(){
        return hashMap;
    }
}
