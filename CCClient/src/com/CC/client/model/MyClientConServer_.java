package com.CC.client.model;

import com.CC.client.tools.ClientConServerThread;
import com.CC.client.tools.ManageClientConServerThread;
import com.CC.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author wqy
 * @version 1.0
 * 客户端连接后台
 */
public class MyClientConServer_ {

    public Socket s;
    public User u;
    public MyClientConServer_(){
//        try {
//            Socket s = new Socket("127.0.0.1",9999);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
    }
    //发送第一次请求
    public boolean sendLoginInfoToServer(Object o){
        boolean b=false;
        try {
            System.out.println("开始登录");
            s = new Socket("127.0.0.1",8888);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(s.getOutputStream());
            objectOutputStream.writeObject(o);
            //从服务器获得读取对象
            ObjectInputStream objectInputStream=new ObjectInputStream(s.getInputStream());

            Message message=(Message)objectInputStream.readObject();
            //这里就是验证用户登录的地方
            if(message.getMessageType().equals("1")){
                //创建一个该qq号与服务器端保持连接的线程
                ClientConServerThread clientConServerThread=new ClientConServerThread(s);
                //启动该通信线程
                clientConServerThread.start();
                ManageClientConServerThread.addClientConServerThread(((User)o).getUserID(),clientConServerThread);
                b = true;
            }else {
                s.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {

        }
        return b;
    }

    public void logout(){
        Message message=new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserID());

        //发送message
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserID()).getS().getOutputStream());
            objectOutputStream.writeObject(message);
            System.out.println(u.getUserID()+"退出系统");
            System.exit(0);//结束进程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SendInfoToServer(Object o){
//        try {
//            Socket s = new Socket("127.0.0.1",9999);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
    }
}
