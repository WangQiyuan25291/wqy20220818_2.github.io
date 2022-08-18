package com.CC.server.model;

import com.CC.common.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wqy
 * @version 1.0
 * 服务Server,它在监听等待某个客户端来连接
 */
public class MyServer_ {



    public MyServer_(){
        try {
            //8888端口监听
            System.out.println("我是服务器,在8888监听");
            ServerSocket ss=new ServerSocket(8888);
            while(true) {
                //阻塞，等待连接
                Socket s = ss.accept();
                //接收客户端发来的信息,拆分的方式不安全,以对象流处理会安全的多
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            String info=bufferedReader.readLine();
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                User u = (User) objectInputStream.readObject();
                System.out.println("服务器接收到用户" + u.getUserID() + "密码" + u.getPasswd());
                Message message = new Message();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                if (u.getPasswd().equals("123456")) {
                    //返回成功登录的信息包

                    message.setMessageType("1");
                    objectOutputStream.writeObject(message);
                    //这里就单开一个线程，让该线程与该客户端保持通信
                    SerConClientThread serConClientThread = new SerConClientThread(s);
                    ManageClientThread.addClientThread(u.getUserID(),serConClientThread);
                    //启动与该客户端通信
                    serConClientThread.start();
                    serConClientThread.notifyOther(u.getUserID());
                } else {
                    message.setMessageType("2");
                    objectOutputStream.writeObject(message);
                    //关闭连接
                    s.close();
                }


            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {

        }


    }
}
