package com.CC.common;

/**
 * @author wqy
 * @version 1.0
 */
public class User implements java.io.Serializable{
    private String userID;
    private String passwd;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
