package com.springboot.dlc.controller.system;


import com.github.liujiebang.pay.ali.config.AliConfig;
import com.github.liujiebang.pay.utils.XMLUtil;
import com.github.liujiebang.pay.wx.service.WxPayService;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.utils.StripePayApi;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: liujiebang
 * @Description: 支付
 * @Date: 2018/7/2 16:30
 **/
@Slf4j
@RestController
public class PayController {


    @Autowired
    private WxPayService wxPayService;
    /**
     * 支付
     *
     * @param outTradeNo 支付流水号
     * @return
     */
    @PostMapping(value = "/front/pay")
    public ResultView pay(HttpServletRequest request, String outTradeNo, String payType) {
        //wxPayService.wxReturn()
        String userId = (String) request.getAttribute(ResultStatus.USER_ID);
        Map map = null;
        return ResultView.ok(map);
    }

    /**
     * stripe Pay
     *
     * @return
     * @throws StripeException
     */
    @PostMapping(value = "/stripe/pay")
    public ResultView stripePay(String stripeToken,
                                String stripeTokenType,
                                String stripeEmail,
                                String orderId){
        //stripeToken
        //token
        log.info(stripeToken);
        log.info(stripeTokenType);
        log.info(stripeEmail);
        //自己的订单号，查询支付金额
        log.info(orderId);
        ResultView resultView=StripePayApi.stripePay(stripeToken,"999",null,null);
        if (resultView.getCode() == 1){
            //业务逻辑
            String id= (String) resultView.getData();

        }
        return ResultView.ok();
    }

    /**
     * stripe refund
     * @param paymentFolw
     * @return
     */
    @PostMapping("/stripe/refund")
    public ResultView refund(String paymentFolw){
        return StripePayApi.refund(paymentFolw);
    }


    /**
     * 支付宝异步回调方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/zfb/notify")
    public String zfbNotify(HttpServletRequest request) {
        try {
            Map<String, String> map = XMLUtil.aliPayNotify(request);
            if (AliConfig.TradeStatus.TRADE_SUCCESS.equals(map.get("trade_status"))) {
                String outTradeNo = map.get("out_trade_no");
                System.out.println("支付宝回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                pay(outTradeNo, "支付宝");
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("---------------------------回调通知异常！！！-------------------------------");
            return "fail";
        }
        return "fail";
    }

    /**
     * 微信支付异步回调方法
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/wcPay/notify")
    public String wcPayNotify(HttpServletRequest request) {
        try {
            Map<String, String> map = XMLUtil.wxPayNotify(request);
            if (ResultStatus.SUCCESS.equals(map.get("return_code"))
                    && ResultStatus.SUCCESS.equals(map.get("result_code"))) {
                    String outTradeNo = map.get("out_trade_no");
                    System.out.println("微信回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                    pay(outTradeNo, ResultStatus.PAY_TYPE_WECHATPAY);
                    return XMLUtil.setWechatXml("SUCCESS", "OK");

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("---------------------------回调通知异常！！！-------------------------------");
            return XMLUtil.setWechatXml("FAIL", "回调通知异常");
        }
        return XMLUtil.setWechatXml("FAIL", "订单支付失败");
    }


    /**
     * 支付回调通用业务逻辑
     *
     * @param outTradeNo 支付流水号
     * @param payType    支付类型
     */
    private void pay(String outTradeNo, String payType) {

        String sub = outTradeNo.substring(0, 3);
    }


}
