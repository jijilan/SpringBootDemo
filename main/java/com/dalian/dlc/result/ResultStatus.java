package com.dalian.dlc.result;

public class ResultStatus {

    public static final String DALIANPARKING = "dalianParking_";

    public static final String USER_ID = "userId";

    public static final String MANAGER_ID = "managerId";

    public static final String USER = "user";

    public static final String MANAGER = "manager";

    public static final String TOKEN = "authorization";

    public static final String WX_APP = "APP";//微信App支付

    public static final String WX_NATIVE = "NATIVE";//微信网页支付

    public static final String WX_JSAPI = "JSAPI";//微信公众号

    public static final String ALI_APP = "AL_APP";//支付宝APP

    public static final String ALI_WEB = "ALI_WEB";//支付宝网页

    // 1：微信  2：QQ  3：微博  4:普通登陆
    public static final int WX_OPENID = 1;

    public static final int QQ_OPENID = 2;

    public static final int WB_OPENID = 3;

    public static final int PHONE_LOGIN = 4;

    //用户token的有效期---7天
    public static final int TONKEN_OUT_TIME = 604800;

}
