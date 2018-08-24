package com.czjsharebed.dlc.result;
/**
 * @Author: liujiebang
 * @Description: 常量
 * @Date: 2018/7/2 16:54
 **/
public class ResultStatus {

    public static final String CZJSHAREBED = "czjShareBed_";

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

    public static final String WX_SP = "WX_SP";//微信小程序

    public static final String SESSION_KEY="session_key";

    // 1：微信  2：QQ  3：微博  4:普通登陆
    public static final int WX_OPENID = 1;

    public static final int QQ_OPENID = 2;

    public static final int WB_OPENID = 3;

    public static final int PHONE_LOGIN = 4;

    //用户token的有效期---7天
    public static final int TONKEN_OUT_TIME = 604800;

    public static final int ADMIN = 1;

    public static final int TEMPORARY_ADMIN = 2;

    public static final int AGENT = 3;

    //订单状态:1:待支付 2:进行中 3:已完成
    public static final int ORDERSTATUS_PAY = 1;
    public static final int ORDERSTATUS_IN = 2;
    public static final int ORDERSTATUS_Y = 3;

    //1:无效 2:有效---订单表、财务表公用
    public static final int ISFLAG_Y = 2;
    public static final int ISFLAG_N = 1;

    //参数类型:1:押金设置（元） 2:商户提现间隔(天） 3:地图检索范围（m）  4:客服电话设置 5:收费标准(元) 6.充电宝追迟归还天数 7.优惠卷面值设置  8.优惠卷有效天数
    public static final int SYSTEMTYPE_1 = 1;
    public static final int SYSTEMTYPE_2 = 2;
    public static final int SYSTEMTYPE_3 = 3;
    public static final int SYSTEMTYPE_4 = 4;
    public static final int SYSTEMTYPE_5 = 5;
    public static final int SYSTEMTYPE_6 = 6;
    public static final int SYSTEMTYPE_7 = 7;
    public static final int SYSTEMTYPE_8 = 8;

    //商户类型
    public static final int MANAGERTYPE_3=3;

    public static final String EQUIPMENTID_TOP = "EQ";
    public static final String BATTERY_TOP = "BA";
    public static final String BUSINESS_TOP = "BUS";
    public static final String MANAGER_TOP = "MA";
    public static final String FEEDBACK_TOP = "FE";
    public static final String RECHARGE_TOP = "RE";
    public static final String RECHARGE_OUTTRADENO_TOP = "ROG";
    public static final String ORDER_TOP = "ORD";
    public static final String OUTTRADENO_TOP = "OUT";
    public static final String LOSE_TOP = "OLE";
    public static final String FINANCE_TOP = "FIN";
    public static final String MESSAGE_TOP = "MES";
    public static final String CASHREQUEST_TOP = "CAS";
    public static final String USER_TOP = "US";
    public static final String COUPON_TOP = "COU";

    /**
     * 通过
     */
    public static final String TRUE = "true";
    /**
     * 驳回
     */
    public static final String FALSE = "false";

    //支付类型(1-余额  2-微信)
    public static final int PAYTYPE_BALANCE = 1;
    public static final int PAYTYPE_WX= 2;

    //财务类型：1:充值 2:订单支出 3:押金缴纳 4:押金退款 5:订单收益 6:提现
    public static final int FINANCETYPE_1 = 1;
    public static final int FINANCETYPE_2 = 2;
    public static final int FINANCETYPE_3 = 3;
    public static final int FINANCETYPE_4 = 4;
    public static final int FINANCETYPE_5 = 5;
    public static final int FINANCETYPE_6 = 6;

    //财务支付类型：1.余额 2.微信 3.支付宝 4.优惠卷
    public static final int FINANCE_PAYTYPE_1 = 1;
    public static final int FINANCE_PAYTYPE_2 = 2;
    public static final int FINANCE_PAYTYPE_3 = 3;
    public static final int FINANCE_PAYTYPE_4 = 4;

    //财务类型:1:余额 2:保证金
    public static final int FINANCE_BALANCETYPE_1 = 1;
    public static final int FINANCE_BALANCETYPE_2 = 2;

    //消息类型 1：我的消息 2：通知
    public static final int MESSAGE_1 = 1;
    public static final int MESSAGE_2 = 2;

    //1:空闲 2:使用中 3:故障 4:报失
    public static final int BATTERY_STATUS_1=1;
    public static final int BATTERY_STATUS_2=2;
    public static final int BATTERY_STATUS_3=3;
    public static final int BATTERY_STATUS_4=4;

    /**
     * 用户类型【1.普通用户 2.维修人员 3.保洁人员】
     */
    public static final int WXUSER_USERTYPE_1=1;
    public static final int WXUSER_USERTYPE_2=3;
    public static final int WXUSER_USERTYPE_3=3;

    /**
     * 用户是否是购买商【1.否 2.是】
     */
    public static final int WXUSER_ISPURCHASER_1=1;
    public static final int WXUSER_ISPURCHASER_2=2;

    /**
     * 用户状态 0.授权中 1:正常 2:禁用
     */
    public static final int WXUSER_ISFLAG_0=0;
    public static final int WXUSER_ISFLAG_1=1;
    public static final int WXUSER_ISFLAG_2=2;



    /**
     * 1:充值 2:共享订单 3.购买订单 4.设备收益 5:押金缴纳 6:押金退款 7:提现
     */
    public static final int WXFINACE_FINANCEETYPE_1=1;
    public static final int WXFINACE_FINANCEETYPE_2=2;
    public static final int WXFINACE_FINANCEETYPE_3=3;
    public static final int WXFINACE_FINANCEETYPE_4=4;
    public static final int WXFINACE_FINANCEETYPE_5=5;
    public static final int WXFINACE_FINANCEETYPE_6=6;
    public static final int WXFINACE_BFINANCEETYPE_7=7;


    /**
     * 财务类型:1:余额 2:保证金
     */
    public static final int WXFINACE_BALANCETYPE_1=1;
    public static final int WXFINACE_BALANCETYPE_2=2;

    /**
     * 图片保存地址
     */

    public static final String FEEDBACK_IMG_PATH = "feedbackImg";

    public static final String CATEGORY_IMG_PATH = "categoryImg";

    public static final String HOSPITAL_IMG_PATH = "hospitalImg";

}
