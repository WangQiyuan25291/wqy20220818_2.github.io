package com.CC.client.tools;

import com.CC.client.view.CCFriendList;

import java.util.HashMap;

/**
 * @author wqy
 * @version 1.0
 * 管理好友、黑名单、陌生人界面
 */
public class ManageCCFriendList {
    private static HashMap hashMap=new HashMap<String, CCFriendList>();
    public static void addCCFriendList(String id,CCFriendList ccFriendList){
        hashMap.put(id,ccFriendList);
    }
    public static CCFriendList getCCFriendList(String id){
        return (CCFriendList)hashMap.get(id);
    }

}
