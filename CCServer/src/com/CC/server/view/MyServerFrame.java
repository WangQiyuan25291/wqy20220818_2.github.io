package com.CC.server.view;

import com.CC.server.model.MyServer_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wqy
 * @version 1.0
 * 这是服务器端的控制界面，可以完成启动服务器，关闭服务器
 * 看到在先的用户，可以监控用户
 */
public class MyServerFrame extends JFrame implements ActionListener {


    JPanel jp1,jp2;
    JButton jb1,jb2;
    JButton jb3;
    JTextField jb3_jtf;
    JLabel jp3_jbl1;
    public static void main(String[] args) {
        MyServerFrame myServerFrame = new MyServerFrame();
    }
    public MyServerFrame(){
        //启动服务器，关闭服务器
        jp1=new JPanel();
        jb1=new JButton("启动服务器");
        jb1.addActionListener(this);
        jb2=new JButton("关闭服务器");
        jb2.addActionListener(this);
        jp1.add(jb1);
        jp1.add(jb2);
        //服务端推送新闻
        jp2=new JPanel(new GridLayout(1,3));
        jp3_jbl1=new JLabel("发送新闻",JLabel.CENTER);
        jb3=new JButton("发送");
        jb3.addActionListener(this);
        jb3_jtf=new JTextField();
        jp2.add(jp3_jbl1);
        jp2.add(jb3_jtf);
        jp2.add(jb3);
        this.add(jp1,"North");
        this.add(jp2,"South");
        this.setSize(500,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb1){
            new MyServer_();
        }
        if (e.getSource()==jb2){
            System.exit(1);
        }
    }
}
