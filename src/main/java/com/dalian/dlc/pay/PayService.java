package com.dalian.dlc.pay;

import com.dalian.dlc.pay.ali.AliPay;
import com.dalian.dlc.pay.wx.WxPay;
import com.dalian.dlc.result.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: liujiebang
 * @Description: 支付通用类
 * @Date: 2018/7/2 16:53
 **/
@Component
public class PayService {

    @Autowired
    private AliPay aliPay;

    @Autowired
    private WxPay wxPay;

    /**
     * 支付通用
     * @param ordersId 支付单号
     * @param moeny 金额
     * @param payType 类型
     * @param openId 微信授权openId
     * @return
     * @return
     */
    public Map pay(String ordersId, double moeny, String payType, String openId){
        if (ResultStatus.WX_APP.equals(payType)||ResultStatus.WX_NATIVE.equals(payType) || ResultStatus.WX_JSAPI.equals(payType)){
            return wxPay.wxPay(ordersId,0.01,payType,openId);
        }else if (ResultStatus.ALI_APP.equals(payType) || ResultStatus.ALI_WEB.equals(payType)){
            return aliPay.aliPay(ordersId,0.01,payType);
        }
        return null;
    }



    /**
     * 微信退款
     * @param ordersId 支付流水号
     * @param allMoney 订单金额
     * @param money 退款金额
     * @param payType  JSAPI 为公众号 APP为APP
     * @return
     */
    public boolean Refund(String ordersId, double allMoney, double money, String payType){
        return wxPay.wxRefund(ordersId, 0.01, 0.01, payType);
    }

    /**
     * 支付宝退款
     * @param orderId 支付流水号
     * @param price 退款金额
     * @return
     */
    public boolean zfbReturn(String orderId,double price) {
        return aliPay.zfbReturn(orderId, 0.01);
    }

}
