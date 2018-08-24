package com.czjsharebed.dlc.model;

import java.io.Serializable;
import java.util.Date;

public class WxUser implements Serializable {
    /** 用户主键 */
    private String userId;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像 */
    private String avatarUrl;

    /** 性别【0:未知 1:男 2:女】 */
    private Integer gender;

    /** 城市 */
    private String city;

    /** 省 */
    private String province;

    /** 国家 */
    private String country;

    /** 手机号码 */
    private String userPhone;

    /** openId */
    private String openId;

    /** 用户类型【1.普通用户 2.维修人员 3.保洁人员】 */
    private Integer userType;

    /** 是否是购买商【1.否 2.是】 */
    private Integer isPurchaser;

    /** 公司名称【保洁售后人员有】 */
    private String companyName;

    /** 公司电话【保洁售后人员有】 */
    private String companyPhone;

    /** 0.授权中 1:正常 2:禁用 */
    private Integer isFlag;

    /** 创建时间 */
    private Date cTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getIsPurchaser() {
        return isPurchaser;
    }

    public void setIsPurchaser(Integer isPurchaser) {
        this.isPurchaser = isPurchaser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
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