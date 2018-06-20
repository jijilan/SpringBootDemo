package com.dalian.dlc.pay.ali;


import com.dalian.dlc.result.ResultStatus;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class AliPay {

    @Autowired
    private AlipayUtil alipayUtil;

    /**
     * 支付宝支付
     * @param orderId 订单号
     * @param payAmount 支付金额
     * @param payType 类型
     * @return
     */
    public Map aliPay(String orderId, double payAmount, String payType) {
        Map<String,Object> map=new HashMap();
        if (payType.equals(ResultStatus.ALI_APP)){
            map.put("aliData",alipayUtil.AliAppPay(orderId,payAmount));
        }else if (payType.equals(ResultStatus.ALI_WEB)){
            map.put("aliData",alipayUtil.AliWebPay(orderId,payAmount));
        }
        return map;
    }

    /**
     * 查询支付宝订单
     * @param ordersId 订单号
     * @return
     */
    public JSONObject findZFBOrder(String ordersId){
        JSONObject jsonObject=alipayUtil.findZFBOrder(ordersId);
        return  jsonObject;
    }


    /**
     * 支付宝订单退款
     * @param orderId 订单号
     * @param payAmount 退款金额
     * @return
     */
    public boolean zfbReturn(String orderId,double payAmount) {
        return alipayUtil.zfbReturn(orderId, payAmount);
    }


    /**
     * 支付宝转账
     * @param alipayAccount 
     * @param payAmount
     * @return
     */
    public boolean alipayTransfer(String alipayAccount,double payAmount){
        return alipayUtil.alipayTransfer(alipayAccount,payAmount);
    }

}
