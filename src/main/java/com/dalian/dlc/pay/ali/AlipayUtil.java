package com.dalian.dlc.pay.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.dalian.dlc.config.PayConfig;
import com.dalian.dlc.utils.IdentityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AlipayUtil {
    private static final String BODY = "迪尔西科技股份有限公司";
    private static final String SUBJECT = "订单结算";
    private static final String TIMEOUTEXPRESS = "30m";
    private static final String PRODUCTCODE = "QUICK_MSECURITY_PAY";

    @Autowired
    private PayConfig payConfig;
    /**
     * 支付宝APP
     * @param orderId
     * @param payAmount
     * @return
     */
    public String AliAppPay(String orderId, double payAmount) {
        String aliPayData = "";
        AlipayClient alipayClient = null;
        alipayClient = new DefaultAlipayClient(payConfig.getZfb_gateway(), payConfig.getZfb_appId_user(),
                payConfig.getZfb_private_key_user(), payConfig.getZfb_Object(), payConfig.getZfb_charset(), payConfig.getZfb_public_key_user(), payConfig.getZfb_sign_type());
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(BODY);
        model.setSubject(SUBJECT);
        model.setOutTradeNo(orderId);
        model.setTimeoutExpress(TIMEOUTEXPRESS);
        model.setTotalAmount(String.valueOf(payAmount));
        model.setProductCode(PRODUCTCODE);
        request.setBizModel(model);
        request.setNotifyUrl(payConfig.getZfb_notifyUrl());
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            aliPayData = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return aliPayData;
    }

    /**
     * 支付宝WEB
     * @param orderId
     * @param payAmount
     * @return
     */
    public String AliWebPay(String orderId, Double payAmount) {
        AlipayClient alipayClient = new DefaultAlipayClient(payConfig.getZfb_gateway(), payConfig.getZfb_appId_user(),
                                                            payConfig.getZfb_private_key_user(), payConfig.getZfb_Object(),
                                                            payConfig.getZfb_charset(), payConfig.getZfb_public_key_user(), payConfig.getZfb_sign_type()); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(payConfig.getZfb_returnUrl());//回调地址
        alipayRequest.setNotifyUrl(payConfig.getZfb_notifyUrl());//通知地址
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(BODY);
        model.setOutTradeNo(orderId);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setTotalAmount(String.valueOf(payAmount));
        model.setSubject(SUBJECT);
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 查询支付宝订单
     * @param ordersId
     * @return
     */
    public  JSONObject findZFBOrder(String ordersId) {
        JSONObject jsonObject=new JSONObject();
        AlipayClient alipayClient = new DefaultAlipayClient(payConfig.getZfb_gateway(),payConfig.getGzh_appId(),
                                                            payConfig.getZfb_private_key(),payConfig.getZfb_Object(),
                                                            payConfig.getZfb_charset(),payConfig.getZfb_public_key(),payConfig.getZfb_sign_type());

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
        model.setOutTradeNo(ordersId);
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
            String resultBody=response.getBody();
            JSONObject resultBodyJSON= JSONObject.fromObject(resultBody);
            JSONObject orderStatus=resultBodyJSON.getJSONObject("alipay_trade_query_response");
            if(orderStatus.get("code").equals("40004")){
                jsonObject.put("status","-1001");
            }
            if(orderStatus.get("code").equals("10000")&&orderStatus.get("trade_status").equals("TRADE_SUCCESS")){
                jsonObject.put("status","0");
            }
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("status","-1001");
        }
        return  jsonObject;
    }


    /**
     * 支付宝退款
     * @param ordersId
     * @param payAmount
     * @return
     * @throws com.alipay.api.AlipayApiException
     */
    public  boolean zfbReturn(String ordersId,double payAmount)  {
        AlipayClient alipayClient = new DefaultAlipayClient(payConfig.getZfb_gateway(), payConfig.getZfb_appId_user(), payConfig.getZfb_private_key_user(), payConfig.getZfb_Object(), payConfig.getZfb_charset(), payConfig.getZfb_public_key_user(), payConfig.getZfb_sign_type());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model=new  AlipayTradeRefundModel();
        model.setOutTradeNo(ordersId);
        model.setRefundAmount(String.valueOf(payAmount));
        model.setOutRequestNo(IdentityUtil.uuid());
        request.setBizModel(model);
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            log.info("支付宝退款结果{}",response.toString());
            if(response.isSuccess()) {
                log.info("-------------------支付宝退款成功------------------");
                return true;
            }
            log.info("-------------------支付宝退款失败------------------");
            return false;
        }catch (AlipayApiException e){
            log.info("-------------------支付宝退款失败------------------");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 支付宝转账
     * @param alipayAccount
     * @param payAmount
     * @return
     */
    public boolean alipayTransfer(String alipayAccount,double payAmount){
        JSONObject jsonObject = new JSONObject();
        try {
            AlipayClient alipayClient = null;
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            AlipayFundTransToaccountTransferModel model=new AlipayFundTransToaccountTransferModel();
            model.setAmount(String.valueOf(payAmount));
            model.setOutBizNo(IdentityUtil.uuid());
            model.setPayeeAccount(alipayAccount);
            model.setRemark("备注转账");
            model.setPayeeType("ALIPAY_LOGONID");
            request.setBizModel(model);
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws AlipayApiException {
        AlipayUtil alipayUtil=new AlipayUtil();
        System.out.println(alipayUtil.AliAppPay("3453434484848",0.01));
        //System.out.println(alipayUtil.zfbReturn("",0.01));
    }
}
