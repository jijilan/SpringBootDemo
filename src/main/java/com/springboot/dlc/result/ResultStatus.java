package com.springboot.dlc.result;

/**
 * @Author: liujiebang
 * @Description: 常量
 * @Date: 2018/7/2 16:54
 **/
public class ResultStatus {

    public static final String PROJECT_NAME = "springboot_";

    /** 权限前缀 */
    public static final String AUTHORITY = PROJECT_NAME + "authority_";

    public static final String TOKEN = "Authorization";

    public static final int TONKEN_OUT_TIME = 604800;

    public static final String USER = "user";

    public static final String USER_ID = "userId";

    public static final String MANAGER = "manager";

    public static final String MANAGER_ID = "managerId";

    public static final String AGENT = "agent";

    public static final String AGENT_ID = "agentId";

    /**
     * 支付方式（1：支付宝支付 ，2：微信支付）
     */
    public static final int PAY_TYPE_ALIPAY = 1;
    public static final int PAY_TYPE_WECHATPAY = 2;

    //1:模块 2:菜单 3:按钮
    public static final int INTERFACETYPE_1 = 1;
    public static final int INTERFACETYPE_2 = 2;
    public static final int INTERFACETYPE_3 = 3;

    /**
     * 管理员类型(1:超级管理员, 2:商户 ,3:普通管理员)
     */
    public static final int MANAGER_ADMIN=1;
    public static final int MANAGER_USER=2;
    public static final int MANAGER_GENERAL=3;

    /** 是否有效（1:是  2:否） */
    public static final int ISFLAG_Y = 1;
    public static final int ISFLAG_N = 2;

    public static final String SUCCESS="SUCCESS";
    public static final int JURISDICTION_MODULAR=1;
    public static final int JURISDICTION_MENU=2;
    public static final int JURISDICTION_BUTTON=2;

    /**
     * 设备状态:0.成功 1.异常 2.失败
     */
    public static final int MACHINE_CLOUD_STATUS_SUCCESS=0;

    public static final int MACHINE_CLOUD_STATUS_ERROR=1;

    public static final int MACHINE_CLOUD_STATUS_FIAL=2;
}
