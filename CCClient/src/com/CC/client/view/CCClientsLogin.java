package com.CC.client.view;

import com.CC.client.model.MyUser;
import com.CC.client.tools.ManageCCFriendList;
import com.CC.client.tools.ManageClientConServerThread;
import com.CC.common.Message;
import com.CC.common.MessageType;
import com.CC.common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author wqy
 * @version 1.0
 *
 * 登录界面
 */

public class CCClientsLogin extends JFrame implements ActionListener{
    //定义上部组件
    JLabel jbl1;

    //定义中部组件
    //中部有三个，由一个叫选项卡窗口进行管理
    JTabbedPane jtp;
    JPanel jp2;
    JPanel jp3;
    JPanel jp4;
    JLabel jp2_jbl1,jp2_jbl2,jp2_jbl3,jp2_jbl4;
    JButton jp2_jb1;
    JTextField jp2_jtf;
    JPasswordField jp2_jpf;
    JCheckBox jp2_jcb1,jp2_jcb2;

    //定义下部组件
    JPanel jp1;
    JButton jp1_jb1,jp2_jb2,jp3_jb3;


    public static void main(String[] args) {
        CCClientsLogin ccClientsLogin=new CCClientsLogin();
    }

    public CCClientsLogin(){
        //处理北部
        jbl1=new JLabel(new ImageIcon("image/tou.gif"));
        //处理中部
        jp2=new JPanel(new GridLayout(3,3));
        jp2_jbl1=new JLabel("登录号码",JLabel.CENTER);
        jp2_jbl2=new JLabel("登录密码",JLabel.CENTER);
        jp2_jbl3=new JLabel("忘记密码",JLabel.CENTER);
        jp2_jbl3.setForeground(Color.BLUE);
        jp2_jbl4=new JLabel("申请密码保护",JLabel.CENTER);
        jp2_jbl4.setForeground(Color.PINK);
        jp2_jb1=new JButton(new ImageIcon("image/clear.gif"));
        jp2_jtf=new JTextField();
        jp2_jpf=new JPasswordField();
        jp2_jcb1=new JCheckBox("隐身登录");
        jp2_jcb2=new JCheckBox("记住密码");
        //把控件按照顺序加入到jp2
        jp2.add(jp2_jbl1);
        jp2.add(jp2_jtf);
        jp2.add(jp2_jb1);
        jp2.add(jp2_jbl2);
        jp2.add(jp2_jpf);
        jp2.add(jp2_jbl3);
        jp2.add(jp2_jcb1);
        jp2.add(jp2_jcb2);
        jp2.add(jp2_jbl4);
        //创建选项卡窗口

        jtp=new JTabbedPane();
        jtp.add("登录号码",jp2);
        jp3=new JPanel();
        jp4=new JPanel();
        jtp.add("手机号码",jp3);
        jtp.add("电子邮件",jp4);
        //处理南部
        jp1=new JPanel();
        jp1_jb1=new JButton(new ImageIcon("image/denglu.gif"));
        //响应用户点击登录
        jp1_jb1.addActionListener(this);
        jp2_jb2=new JButton(new ImageIcon("image/quxiao.gif"));
        jp3_jb3=new JButton(new ImageIcon("image/xiangdao.gif"));
        jp1.add(jp1_jb1);
        jp1.add(jp2_jb2);
        jp1.add(jp3_jb3);

        this.add(jbl1,"North");
        this.add(jtp,"Center");
        //把jp1放在南面
        this.add(jp1,"South");
        this.setSize(540,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //如果用户点击登录了
        if (e.getSource()==jp1_jb1){
            MyUser myUser = new MyUser();
            User u=new User();
            u.setUserID(jp2_jtf.getText().trim());
            u.setPasswd(new String(jp2_jpf.getPassword()));

            if (myUser.checkUser(u)){
                //发送一个要求返回在线好友的包
                try {
                    //把创建好友列表的语句提前
                    CCFriendList ccFriendList = new CCFriendList(u.getUserID());
                    ManageCCFriendList.addCCFriendList(u.getUserID(),ccFriendList);


                    ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserID()).getS().getOutputStream());
                    //做一个Message包
                    Message m=new Message();
                    m.setMessageType(MessageType.message_get_onLineFriend);
                    //指明我要的是这个QQ号的好友情况
                    m.setSender(u.getUserID());
                    oos.writeObject(m);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                this.dispose();//关闭登录界面
            }else {
                JOptionPane.showMessageDialog(this,"用户名密码错误");
            }
        }

    }
}
