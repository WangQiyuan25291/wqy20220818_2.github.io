package com.CC.client.tools;

import com.CC.client.view.CCChat;
import com.CC.client.view.CCFriendList;
import com.CC.common.Message;
import com.CC.common.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author wqy
 * @version 1.0
 * 客户端和服务器保持通讯的线程
 */
public class ClientConServerThread extends Thread{

    private Socket s;

    public ClientConServerThread(Socket s) {
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    public void run(){
        while (true){
            //不停的读取从服务器端发来的消息
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                Message m=(Message)objectInputStream.readObject();
                System.out.println("读取到从服务器发来的消息"+m.getSender()+"给"+m.getGetter()+"内容"+m.getCont());
                //把服务器获得的消息，显示到该显示的聊天界面
                if(m.getMessageType().equals(MessageType.message_comm_mes)) {
                    //把服务器端的界面返回到该显示的聊天界面
                    CCChat ccChat = ManageCCChat.getCCChat(m.getGetter() + " " + m.getSender());
                    //显示
                    ccChat.showMessage(m);
                }else if(m.getMessageType().equals(MessageType.message_ret_onLineFriend)){
                    String con=m.getCont();
                    String friends[]=con.split(" ");
                    String getter=m.getGetter();
                    //修改相应的好友列表.
                    CCFriendList ccFriendList = ManageCCFriendList.getCCFriendList(getter);
                    //更新在线好友
                    if(ccFriendList!=null) {
                        ccFriendList.updateFriend(m);
                    }
                }else if(m.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    //显示在我们的客户端的控制台
                    CCChat ccChat = ManageCCChat.getCCChat(m.getGetter() + " " + m.getSender());
                    //显示
                    ccChat.showMessage2(m);
                }else if(m.getMessageType().equals(MessageType.MESSAGE_FILE_MES)){
                    System.out.println("\n"+m.getSender()+"给"+m.getGetter()+"发送文件"+m.getSrc()+"到我的电脑的目录"+m.getDest());
                    //取出message文件字节数组，通过文件输出流写出磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(m.getDest());
                    fileOutputStream.write(m.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("保存文件成功~");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
