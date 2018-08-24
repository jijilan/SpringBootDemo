package com.czjsharebed.dlc.model;

import java.io.Serializable;
import java.util.Date;

public class SysManager  implements Serializable {
    /** 管理员编号 */
    private String managerId;

    /** 用户名称 */
    private String userName;

    /** 用户账号 */
    private String userAcount;

    /** 用户密码 */
    private String passWord;

    /** 1:正常 2:禁用 */
    private Integer isFlag;

    /** 创建时间 */
    private Date cTime;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserAcount() {
        return userAcount;
    }

    public void setUserAcount(String userAcount) {
        this.userAcount = userAcount == null ? null : userAcount.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }
}