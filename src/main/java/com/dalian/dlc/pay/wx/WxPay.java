package com.dalian.dlc.pay.wx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;

@Slf4j
@Component
public class WxPay {

    @Autowired
    private WxPayUtil wxPayUtil;
    public Map wxPay(String orderId, double payAmount, String payType,String openId){
        return wxPayUtil.wxPay(orderId,payAmount,payType,openId);
    }

    /**
     * 微信退款
     * @param ordersId 支付流水号
     * @param allMoney 订单金额
     * @param money 退款金额
     * @param payType  JSAPI 为公众号 APP为APP
     * @return
     */
    public boolean wxRefund(String ordersId, double allMoney, double money, String payType){
        Map<String,Object> map = wxPayUtil.wxRefund(ordersId, 0.01, 0.01, payType);
        if(map != null && map.get("return_code").equals("SUCCESS")){
            log.info("------------------微信退款成功---------------------");
            return true;
        }
        log.info("------------------微信退款失败---------------------");
        return false;
    }

    /**
     * 支付结果查询
     *
     * @param ordersId
     * @return INTEGER 【0:查询异常 1：支付成功 2：转入退款  3未支付 4已关闭 5已撤销 6用户支付中 7支付失败】
     */
    public int wxQuery(String ordersId, String payType) {
        return wxPayUtil.wxQuery(ordersId,payType);
    }
}
