package com.springboot.dlc.utils;


import com.springboot.dlc.exception.PayException;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StripePayApi {

    public static final String PK_LIVE_STRIPE_APIKEY="pk_live_5iF7sC2Or8gWGj0uM7hrzLeT";
    public static final String SK_LIVE_STRIPE_APIKEY = "sk_live_wdP8a3qTvSyEMNGXMipF8CJb";

    public static final String PK_TEST_STRIPE_APIKEY = "pk_test_NIqQRZ5NpErPUyKGkop5K11n";
    public static final String SK_TEST_STRIPE_APIKEY = "sk_test_vOCi7U8e5xDXU5eGVRSNCjZ5";

    /**
     * 支付
     * @param stripeToken 前端授权token
     * @param amount 支付金额
     * @param currency 币种(默认澳币)
     * @param statement_descriptor 支付描述
     */
    public static ResultView stripePay(String stripeToken,String amount,  String currency, String statement_descriptor){
        Stripe.apiKey = SK_TEST_STRIPE_APIKEY;
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isEmpty(currency)){
            currency="usd";
        }
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("description", "Example charge");
        params.put("source", stripeToken);
        if (!StringUtils.isEmpty(statement_descriptor)){
            params.put("statement_descriptor", "Custom descriptor");
        }
        try{
            Charge charge = Charge.create(params);
            if ("succeeded".equals(charge.getStatus())) {
                log.info("支付成功后的支付标识，可用于退款--保存至数据库当支付流水号:" + charge.getId());
                log.info("支付结果----" + charge.getStatus());

                return ResultView.ok(charge.getId());
            } else if ("failed".equals(charge.getStatus())) {
                log.info("拒绝通知结果---" + charge.getOutcome().toJson());
            }
        }catch (StripeException e){
            StringBuffer sb=new StringBuffer(ResultEnum.CODE_1003.getMsg());
            sb.append(". ");
            sb.append(e.getMessage());
            throw new PayException(ResultEnum.CODE_1003,sb.toString());
        }
        return ResultView.error(ResultEnum.CODE_1003);
    }

    public static ResultView refund(String paymentFolw){
        Stripe.apiKey = SK_TEST_STRIPE_APIKEY;
        Map<String, Object> params = new HashMap<>();
        params.put("charge", paymentFolw);
        //全额退款不需要指定退款金额
        //params.put("amount", 999);
        try {
            Refund refund = Refund.create(params);
            if ("succeeded".equals(refund.getStatus())) {
                log.info("退款结果----" + refund.getStatus());
                return ResultView.ok();
            }
        }catch (StripeException e){
            StringBuffer sb=new StringBuffer(ResultEnum.CODE_1004.getMsg());
            sb.append(". ");
            sb.append(e.getMessage());
            throw new PayException(ResultEnum.CODE_1004,sb.toString());
        }
        return ResultView.error(ResultEnum.CODE_1004);
    }
}
