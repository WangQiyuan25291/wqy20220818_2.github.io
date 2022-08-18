package com.CC.client.view;

import com.CC.client.model.MyClientConServer_;
import com.CC.client.tools.ManageCCChat;
import com.CC.client.tools.ManageClientConServerThread;
import com.CC.common.Message;
import com.CC.common.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author wqy
 * @version 1.0
 * 好友列表，包括陌生人和黑名单
 */
public class CCFriendList extends JFrame implements ActionListener,MouseListener{
    //处理第一张卡片
    JPanel jphy1,jphy2,jphy3;
    JButton jphy_jb1,jphy_jb2,jphy_jb3;
    JScrollPane jsp1;
    String ownerId;
    //处理第二张卡片
    JPanel jpmsr1,jpmsr2,jpmsr3;
    JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
    JScrollPane jsp2;
    JLabel[] jb1s;
    public static void main(String[] args) {
        //CCFriendList ccFriendList = new CCFriendList("1");
    }
    //把整个JFrame设置成一个CardLayout布局
    CardLayout cl;
    public CCFriendList(String ownerId){
        this.ownerId=ownerId;
        //处理第一张卡片(显示好友列表)
        jphy_jb1=new JButton("我的好友");
        jphy_jb2=new JButton("陌生人");
        jphy_jb2.addActionListener(this);
        jphy_jb3=new JButton("退出");
        jphy_jb3.addActionListener(this);
        jphy1=new JPanel(new BorderLayout());
        //假定有50个好友
        jphy2=new JPanel(new GridLayout(50,1,4,4));

        //给jphy2，初始化50好友.
         jb1s=new JLabel[50];

        for(int i=0;i<jb1s.length;i++)
        {

            jb1s[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
            jb1s[i].setEnabled(false);
            if(jb1s[i].getText().equals(ownerId)){
                jb1s[i].setEnabled(true);
            }
            jb1s[i].addMouseListener(this);
            jphy2.add(jb1s[i]);


        }

        jphy3=new JPanel(new GridLayout(2,1));
        //把两个按钮加入到jphy3
        jphy3.add(jphy_jb2);
        jphy3.add(jphy_jb3);


        jsp1=new JScrollPane(jphy2);


        //对jphy1,初始化
        jphy1.add(jphy_jb1,"North");
        jphy1.add(jsp1,"Center");
        jphy1.add(jphy3,"South");


        //处理第二张卡片


        jpmsr_jb1=new JButton("我的好友");
        jpmsr_jb1.addActionListener(this);
        jpmsr_jb2=new JButton("陌生人");
        jpmsr_jb3=new JButton("退出");
        jpmsr1=new JPanel(new BorderLayout());
        //假定有20个陌生人
        jpmsr2=new JPanel(new GridLayout(20,1,4,4));

        //给jphy2，初始化20陌生人.
        JLabel []jb1s2=new JLabel[20];

        for(int i=0;i<jb1s2.length;i++)
        {
            jb1s2[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
            jpmsr2.add(jb1s2[i]);
        }

        jpmsr3=new JPanel(new GridLayout(2,1));
        //把两个按钮加入到jphy3
        jpmsr3.add(jpmsr_jb1);
        jpmsr3.add(jpmsr_jb2);


        jsp2=new JScrollPane(jpmsr2);


        //对jphy1,初始化
        jpmsr1.add(jpmsr3,"North");
        jpmsr1.add(jsp2,"Center");
        jpmsr1.add(jpmsr_jb3,"South");


        cl=new CardLayout();
        this.setLayout(cl);
        this.add(jphy1,"1");
        this.add(jpmsr1,"2");
        //在窗口显示自己的编号.
        this.setTitle(ownerId);
        this.setSize(300, 600);
        this.setVisible(true);
    }
    //更新在线好友情况
    public void updateFriend(Message message){

        String onLineFriend[]= message.getCont().split(" ");

        for (int i = 0; i < onLineFriend.length; i++) {
            jb1s[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果点击陌生人按钮，就显示第二张卡片
        if(e.getSource()==jphy_jb2){
            cl.show(this.getContentPane(),"2");
        }else if(e.getSource()==jpmsr_jb1){
            cl.show(this.getContentPane(),"1");
        }else if(e.getSource()==jphy_jb3){
            Message message=new Message();
            message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
            message.setSender(this.ownerId);
            try {
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
                objectOutputStream.writeObject(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //响应用户双击事件并得到好友的编号
        if(e.getClickCount()==2){
            //得到该好友的编号
            String friendNo=((JLabel)e.getSource()).getText();
            System.out.println("你希望和"+friendNo+"聊天");
            CCChat ccChat = new CCChat(this.ownerId,friendNo);
            //把聊天界面加入管理类
            ManageCCChat.addCCChat(this.ownerId+" "+friendNo,ccChat);

//            Thread thread = new Thread(ccChat);
//            thread.start();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jLabel =(JLabel) e.getSource();
        jLabel.setForeground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jLabel =(JLabel) e.getSource();
        jLabel.setForeground(Color.black);
    }
}
