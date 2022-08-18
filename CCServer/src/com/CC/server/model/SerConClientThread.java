package com.CC.server.model;

import com.CC.common.Message;
import com.CC.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author wqy
 * @version 1.0
 * 服务器和某个客户端的通信线程
 */
public class SerConClientThread extends Thread{
    public Socket s;

    public SerConClientThread(Socket s) {
        this.s = s;
    }
    public Socket getSocket(){
        return s;
    }
    //让该线程去通知其他用户
    public void notifyOther(String iam){
        //得到所有在线的人的线程
        HashMap hashMap = ManageClientThread.hashMap;
        Iterator iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            Message m=new Message();
            m.setCont(iam);
            m.setMessageType(MessageType.message_ret_onLineFriend);

            //取出在线人的id
            String onLineUserId= iterator.next().toString();
            try {
                ObjectOutputStream objectOutputStream=new ObjectOutputStream (ManageClientThread.getClient(onLineUserId).s.getOutputStream());
                m.setGetter(onLineUserId);
                objectOutputStream.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void run(){
        //这里该线程就可以接收客户端的信息
        while (true){
            try {
                ObjectInputStream objectInputStream=new ObjectInputStream(s.getInputStream());
                Message m=(Message) objectInputStream.readObject();
                //登录成功后不会发消息，所以会出现null
                //System.out.println(m.getSender()+"给"+m.getGetter()+"说"+m.getCont());
                //对从客户端取得的消息进行类型判断，然后做相应的处理
                if (m.getMessageType().equals(MessageType.message_comm_mes)) {
                    //转发,取得接收人的通讯线程
                    SerConClientThread sc = ManageClientThread.getClient(m.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                    oos.writeObject(m);
                }else if(m.getMessageType().equals(MessageType.message_get_onLineFriend)){
                    System.out.println(m.getSender()+"要她的好友");
                    //把在服务器的好友给该客户端返回

                    String res=ManageClientThread.getAllOnlineUserId();
                    Message message=new Message();
                    message.setMessageType(MessageType.message_ret_onLineFriend);
                    message.setCont(res);
                    message.setGetter(m.getSender());
                    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(message);
                }else if(m.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    //群发消息，遍历管理线程的集合，把socket得到，然后把Message进行转发即可
                    HashMap<String,SerConClientThread> hm=ManageClientThread.getHm();
                    Iterator<String> iterator=hm.keySet().iterator();
                    while (iterator.hasNext()){
                        //取出在线用户的id
                        String onLineUserId=iterator.next().toString();
                        if(!onLineUserId.equals(m.getSender())){//排除群发消息的用户
                            //进行转发
                            m.setGetter(onLineUserId);
                            SerConClientThread sc = ManageClientThread.getClient(m.getGetter());
                            ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                            oos.writeObject(m);
                        }
                    }
                }
                else if(m.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    //客户端退出
                    System.out.println(m.getSender()+"要退出客户端");
                    //将客户端对应的线程从集合中删除
                    ManageClientThread.removeSerConClientThread(m.getSender());
                    
                    s.close();
                    break;
                }else if(m.getMessageType().equals(MessageType.MESSAGE_FILE_MES)){
                    //根据getter id获取到对应的线程，将message对象转发
                    SerConClientThread serConClientThread = ManageClientThread.getClient(m.getGetter());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serConClientThread.getSocket().getOutputStream());
                    //转发：
                    objectOutputStream.writeObject(m);

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
