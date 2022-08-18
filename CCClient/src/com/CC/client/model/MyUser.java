package com.CC.client.model;

import com.CC.common.Message;
import com.CC.common.MessageType;
import com.CC.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wqy
 * @version 1.0
 * 和服务器进行交互
 */
public class MyUser {

    public boolean checkUser(User u){
        return new MyClientConServer_().sendLoginInfoToServer(u);
    }
    //退出客户端



}
