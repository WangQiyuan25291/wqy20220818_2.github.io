package com.CC.client.view;

import com.CC.client.model.MyClientConServer_;
import com.CC.client.tools.FileClient;
import com.CC.client.tools.ManageClientConServerThread;
import com.CC.common.Message;
import com.CC.common.MessageType;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;

/**
 * @author wqy
 * @version 1.0
 * 这是与好友聊天的界面
 * 因为客户端要处于读取的状态，因此我们把它做成一个线程
 */
public class CCChat extends JFrame implements ActionListener{
    JTextArea jta;
    JTextField jtf;
    JButton jb,jb1;
    JPanel jp;
    String ownerId;
    String friendId;
    //发送文件
    private FileClient fileClient=new FileClient();//用于传送文件
    public static void main(String[] args) {
        //CCChat qqChat=new CCChat("2","1");
    }
    public CCChat(String ownerId,String friend){
        this.ownerId=ownerId;
        this.friendId=friend;
        jta=new JTextArea();
        jtf=new JTextField(15);
        jb=new JButton("发送消息");
        jb.addActionListener(this);
        jb1=new JButton("发送文件");
        jb1.addActionListener(this);
        jp=new JPanel();
        jp.add(jtf);
        jp.add(jb);
        jp.add(jb1);

        this.add(jta,"Center");
        this.add(jp,"South");
        this.setTitle(ownerId+"正在和"+friend+"聊天");

        this.setSize(400, 200);
        this.setVisible(true);


    }
    //写一个方法，让其显示消息
    public void showMessage(Message message){
        String info;

        info = message.getSender() + "对" + message.getGetter() + "说" + message.getCont() + "\r\n" + message.getSendTime() + "\r\n";

        this.jta.append(info);
    }
    public void showMessage2(Message message){
        String info;
        info=message.getSender()+"对所有人说"+message.getCont()+"\r\n" + message.getSendTime() + "\r\n";
        this.jta.append(info);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {//点击发送消息按钮
            Message message = new Message();
            if (!this.ownerId.equals(this.friendId)) {
                message.setMessageType(MessageType.message_comm_mes);
                message.setSender(this.ownerId);
                message.setGetter(this.friendId);
                message.setCont(jtf.getText());
                message.setSendTime(new Date().toString());
            } else {//群发消息
                message.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
                message.setSender(this.ownerId);

                message.setCont(jtf.getText());
                message.setSendTime(new Date().toString());
            }
            //发送给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
                oos.writeObject(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } else if (e.getSource() == jb1) {
            System.out.println("请输入你想发送文件的用户(在线)");

            String getterId = friendId;

            System.out.println("请输入发送文件路径");
            int result = 0;
            File file = null;
            String path = null;
            JFileChooser fileChooser = new JFileChooser();
            FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
            System.out.println(fsv.getHomeDirectory());                //得到桌面路径
            fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
            fileChooser.setDialogTitle("请选择要上传的文件...");
            fileChooser.setApproveButtonText("确定");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            result = fileChooser.showOpenDialog(null);
            if (JFileChooser.APPROVE_OPTION == result) {
                path = fileChooser.getSelectedFile().getPath();
                System.out.println("path1: " + path);
            }
            String src = path;
            System.out.println("请输入把文件发送到对方的路径");
            String dest = jtf.getText();
            fileClient.sendFileToOne(src, dest, ownerId, getterId);


        }
    }
//    @Override
//    public void run() {
//        while (true){
//
//            try {
//                //读取(如果读不到就等待)
//                ObjectInputStream ois=new ObjectInputStream(MyClientConServer_.s.getInputStream());
//                Message message=(Message)ois.readObject();
//                //显示
//                String info= message.getSender()+"对"+ message.getGetter()+"说"+ message.getCont()+"\r\r\n"+message.getSendTime()+"\r\r\n";
//                this.jta.append(info);
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
