package com.dalian.dlc.pay.wx;

import com.dalian.dlc.config.PayConfig;
import com.dalian.dlc.pay.wx.tool.*;
import com.dalian.dlc.result.ResultStatus;
import com.dalian.dlc.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j
public class WxPayUtil {

    @Autowired
    private PayConfig payConfig;

    /**
     *  APP支付
     * @param ordersId
     * @param payAmount
     * @return
     */
    public String wxAppPay(String ordersId,double payAmount){
        String sign="";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", payConfig.getWx_appId());
        packageParams.put("mch_id", payConfig.getWx_mchId());
        packageParams.put("body", BODY);
        packageParams.put("nonce_str", IdentityUtil.uuid());
        packageParams.put("notify_url", payConfig.getWx_notifyUrl());
        packageParams.put("out_trade_no", ordersId);
        packageParams.put("spbill_create_ip", IdentityUtil.getLocalhostIp());
        packageParams.put("total_fee", getMoney(String.valueOf(payAmount)));
        packageParams.put("trade_type", "APP");
        sign = createSign("UTF-8", packageParams,payConfig.getWx_key());
        packageParams.put("sign", sign);
        String result= CommonUtil.httpsRequest(UNIFIED_ORDER_URL, "POST", XMLUtil.getRequestXml(packageParams));
        log.info(result);
        return getXMLPayKey(result,"prepay_id");
    }

    /**
     *  微信公众号
     * @param ordersId
     * @param payAmount
     * @param openId
     * @return
     */
    public String wxJSAPIPay(String ordersId,double payAmount,String openId){
        String sign="";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", payConfig.getGzh_appId());
        packageParams.put("mch_id", payConfig.getGzh_mchId());
        packageParams.put("body", BODY);
        packageParams.put("nonce_str", IdentityUtil.uuid());
        packageParams.put("notify_url", payConfig.getWx_notifyUrl());
        packageParams.put("openid",openId);
        packageParams.put("out_trade_no", ordersId);
        packageParams.put("spbill_create_ip", IdentityUtil.getLocalhostIp());
        packageParams.put("total_fee", getMoney(String.valueOf(payAmount)));
        packageParams.put("trade_type","JSAPI");
        sign = createSign("UTF-8", packageParams,payConfig.getGzh_key());
        packageParams.put("sign", sign);
        String result= CommonUtil.httpsRequest(UNIFIED_ORDER_URL, "POST", XMLUtil.getRequestXml(packageParams));
        return getXMLPayKey(result,"prepay_id");
    }

    /**
     *  微信H5支付
     * @param ordersId
     * @param payAmount
     * @return
     */
    public String wxNativePay(String ordersId,double payAmount){
        String sign="";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", payConfig.getWx_appId());
        packageParams.put("mch_id", payConfig.getWx_mchId());
        packageParams.put("body", BODY);
        packageParams.put("nonce_str", IdentityUtil.uuid());
        packageParams.put("notify_url", payConfig.getWx_notifyUrl());
        packageParams.put("out_trade_no", ordersId);
        packageParams.put("spbill_create_ip", IdentityUtil.getLocalhostIp());
        packageParams.put("total_fee", getMoney(String.valueOf(payAmount)));
        packageParams.put("trade_type","NATIVE");
        sign = createSign("UTF-8", packageParams,payConfig.getWx_key());
        packageParams.put("sign", sign);
        String result= CommonUtil.httpsRequest(UNIFIED_ORDER_URL, "POST", XMLUtil.getRequestXml(packageParams));
        log.info(result);
        return getXMLPayKey(result,"prepay_id");
    }

    public SortedMap<String,String> wxPay(String orderId,double payAmount,String payType,String openId){
        String prepayId=null;
        if (ResultStatus.WX_APP.equals(payType)){
            prepayId=wxAppPay(orderId,payAmount);
        }else if (ResultStatus.WX_JSAPI.equals(payType)){
            prepayId=wxJSAPIPay(orderId,payAmount,openId);
        }else if (ResultStatus.WX_NATIVE.equals(payType)){
            prepayId=wxNativePay(orderId,payAmount);
        }
        SortedMap<String,String> packageParams = new TreeMap<String,String>();

        if(payType.equals(ResultStatus.WX_JSAPI)){//再进行签名
            packageParams.put("appId",payConfig.getWx_appId());
            packageParams.put("timeStamp", String.valueOf(Calendar.getInstance().getTimeInMillis()/1000));
            packageParams.put("nonceStr",IdentityUtil.getNonceStr());
            packageParams.put("package","prepay_id="+prepayId);
            packageParams.put("signType","MD5");
            String sign = createSign("UTF-8", packageParams,payConfig.getGzh_key());
            packageParams.put("paySign",sign);
        }else if (payType.equals(ResultStatus.WX_APP)){
            packageParams.put("appid",payConfig.getWx_appId());
            packageParams.put("partnerid",payConfig.getWx_mchId());
            packageParams.put("prepayid",prepayId);
            packageParams.put("partnerkey",payConfig.getWx_key());
            packageParams.put("appsecret",payConfig.getWx_appSecrect());
            packageParams.put("package","Sign=WXPay");
            packageParams.put("noncestr",IdentityUtil.uuid());
            packageParams.put("timestamp", String.valueOf(Calendar.getInstance().getTimeInMillis()/1000));
            packageParams.put("sign", PayCommonUtil.createSign("UTF-8", packageParams,payConfig.getWx_key()));
            packageParams.put("outTradeNo",orderId);
        }else if (payType.equals(ResultStatus.WX_NATIVE)){
            packageParams.put("code_url",prepayId);
        }
        return packageParams;
    }

    /**
     * 支付结果查询
     *
     * @param ordersId
     * @return INTEGER 【0:查询异常 1：支付成功 2：转入退款  3未支付 4已关闭 5已撤销 6用户支付中 7支付失败】
     */
    public int wxQuery(String ordersId, String payType) {
        int result=0;
        try {
            SortedMap<String,String> packageParams = new TreeMap<String,String>();
            packageParams.put("appid", payConfig.getWx_appId());
            packageParams.put("mch_id", payConfig.getWx_mchId());
            packageParams.put("nonce_str",IdentityUtil.uuid());
            packageParams.put("out_trade_no",ordersId);
            if(payType.equals(ResultStatus.WX_JSAPI)){
                packageParams.put("sign", createSign("UTF-8", packageParams, payConfig.getGzh_key()));
            }else if (payType.equals(ResultStatus.WX_APP)|| payType.equals(ResultStatus.WX_NATIVE)){
                packageParams.put("sign", createSign("UTF-8", packageParams, payConfig.getWx_key()));
            }

            Map<String,String> resultMap= XMLUtil.doXMLParse(CommonUtil.httpsRequest(CHECK_ORDER_URL, "POST", XMLUtil.getRequestXml(packageParams)));
            if(resultMap!=null&&resultMap.containsKey("return_code")&&resultMap.containsKey("return_msg")){
                //SUCCESS—支付成功
                //REFUND—转入退款
                //NOTPAY—未支付
                //CLOSED—已关闭
                //REVOKED—已撤销（刷卡支付）
                //USERPAYING--用户支付中
                //PAYERROR--支付失败(其他原因，如银行返回失败)
                if(resultMap.get("trade_state").equals("SUCCESS")){
                    result=1;
                }
                if(resultMap.get("trade_state").equals("REFUND")){
                    result=2;
                }
                if(resultMap.get("trade_state").equals("NOTPAY")){
                    result=3;
                }
                if(resultMap.get("trade_state").equals("CLOSED")){
                    result=4;
                }
                if(resultMap.get("trade_state").equals("REVOKED")){
                    result=5;
                }
                if(resultMap.get("trade_state").equals("USERPAYING")){
                    result=6;
                }
                if(resultMap.get("trade_state").equals("PAYERROR")){
                    result=7;
                }
            }
        } catch (Exception e) {
            return result;
        }
        return  result;
    }

    /**
     * 微信退款
     * @param ordersId 支付流水号
     * @param allMoney 订单金额
     * @param money 退款金额
     * @param payType  JSAPI 为公众号 APP为APP
     * @return
     */
    public Map<String,Object> wxRefund(String ordersId, double allMoney, double money, String payType) {
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", payConfig.getWx_appId());
        packageParams.put("mch_id", payConfig.getWx_mchId());
        packageParams.put("nonce_str", IdentityUtil.getNonceStr());
        packageParams.put("out_trade_no", ordersId);
        packageParams.put("out_refund_no", IdentityUtil.uuid());
        packageParams.put("total_fee", String.valueOf(allMoney));
        packageParams.put("refund_fee", String.valueOf(money));
        if(payType.equals(ResultStatus.WX_JSAPI)){
            packageParams.put("sign", createSign("UTF-8", packageParams, payConfig.getGzh_key()));
        }else if (payType.equals(ResultStatus.WX_APP)|| payType.equals(ResultStatus.WX_NATIVE)){
            packageParams.put("sign", createSign("UTF-8", packageParams, payConfig.getWx_key()));
        }
        try {
            return CommonUtil.getMapFromXML(WXHttpUtil.httpsRequest(REFUND_URL, XMLUtil.getRequestXml(packageParams), payConfig.getWx_certPath(),payConfig.getWx_mchId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String createSign(String characterEncoding, SortedMap<String, String> parameters,String secrect) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" +secrect);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public  String getXMLPayKey(String result,String key ){
        log.info("result"+result+"key"+key);
        Map<String, String> map = XMLUtil.doXMLParse(result);
        String returnCode = map.get("return_code");
        String resultCode = map.get("result_code");
        if(returnCode.equalsIgnoreCase("SUCCESS")&&resultCode.equalsIgnoreCase("SUCCESS")){
            String prepayId=  map.get(key);
            if(map.containsKey("code_url")) {
                prepayId = map.get("code_url");
            }
            return prepayId;
        }else{
            return null;
        }
    }
    public static String getMoney(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
        // 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }


    public static final String BODY="大连停车位";
    //微信支付统一接口(POST)
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款接口(POST)
    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //订单查询接口(POST)
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

}
