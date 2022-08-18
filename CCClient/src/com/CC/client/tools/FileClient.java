package com.CC.client.tools;

import com.CC.common.Message;
import com.CC.common.MessageType;

import java.io.*;

/**
 * @author wqy
 * @version 1.0
 * 完成文件传输服务
 */
public class FileClient {
    public void sendFileToOne(String src,String dest,String senderId,String getterId){
        //读取src文件（必须存在）--封装到Message对象中去
        Message message=new Message();
        message.setMessageType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        //需求：将为文件读取
        FileInputStream fileInputStream=null;
        byte[] fileBytes=new byte[(int)new File(src).length()];

        try {
            fileInputStream=new FileInputStream(src);
            fileInputStream.read(fileBytes);//读进程序的字节数组
            //将文件对应的字节数组，设置到message对象
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println("\n"+senderId+"给"+getterId+"发送文件"+src
                +"到对方的电脑的目录"+dest);


        //发送
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(senderId).getS().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
