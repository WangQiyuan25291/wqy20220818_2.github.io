package com.CC.client.tools;

import com.CC.client.view.CCChat;

import java.util.HashMap;

/**
 * @author wqy
 * @version 1.0
 * 这是一个管理用户聊天界面的类
 */
public class ManageCCChat {
    public static HashMap hm = new HashMap<String, CCChat>();

    //加入
    public static void addCCChat(String loginIdAnFriendId,CCChat ccChat){
        hm.put(loginIdAnFriendId,ccChat);
    }
    //取出
    public static CCChat getCCChat(String loginIdAnFriendId){
        return (CCChat)hm.get(loginIdAnFriendId);
    }
}
