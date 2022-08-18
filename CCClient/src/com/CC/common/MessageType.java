package com.CC.common;

/**
 * @author wqy
 * @version 1.0
 */
public interface MessageType {
    String message_succeed="1";//登录成功
    String message_login_fail="2";//登录失败
    String message_comm_mes="3";//普通信息包
    String message_get_onLineFriend="4";//要求在线好友
    String message_ret_onLineFriend="5";//返回在线好友
    String MESSAGE_CLIENT_EXIT = "6"; //客户端请求退出
    String MESSAGE_TO_ALL_MES = "7"; //群发消息报
    String MESSAGE_FILE_MES="8";//发送文件
}
