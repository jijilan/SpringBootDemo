package com.springboot.dlc.controller.system;


import com.github.liujiebang.pay.utils.XMLUtil;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.exception.PayException;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import lombok.extern.slf4j.Slf4j;
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


    /**
     * 支付
     *
     * @param outTradeNo 支付流水号
     * @return
     */
    @PostMapping(value = "/front/pay")
    public ResultView pay(HttpServletRequest request, String outTradeNo, String payType) {
        String userId = (String) request.getAttribute(ResultStatus.USER_ID);
        Map map = null;

        return ResultView.ok(map);
    }

    /**
     * stripe Pay
     *
     * @param request
     * @return
     * @throws StripeException
     */
    @PostMapping(value = "/stripe/pay")
    public ResultView stripePay(HttpServletRequest request){

        Stripe.apiKey = "sk_live_wdP8a3qTvSyEMNGXMipF8CJb";
        String token = request.getParameter("stripeToken");
        String stripeTokenType = request.getParameter("stripeTokenType");
        String stripeEmail = request.getParameter("stripeEmail");
        log.info(token);
        log.info(stripeTokenType);
        log.info(stripeEmail);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 11);
        params.put("currency", "usd");
        params.put("description", "Example charge");
        params.put("source", token);
        params.put("statement_descriptor", "Custom descriptor");
        try{
            Charge charge = Charge.create(params);
            if ("succeeded".equals(charge.getStatus())) {
                log.info("支付成功后的支付标识，可用于退款" + charge.getId());
                log.info("支付结果----" + charge.getStatus());
                return ResultView.ok();
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

    /**
     * @api {POST} /pay/zfb/notify 支付宝支付后回调方法
     * @apiGroup Pay
     * @apiVersion 1.0.0
     * @apiExample {url} 接口示例
     * curl -i http://120.79.18.21:8080/goodOrders/nlg/zfb/notify
     * @apiSuccessExample {json} 微信成功的响应
     * HTTP/1.1 200 OK
     * @apiSuccessExample {json} 成功的响应
     * HTTP/1.1 200 OK
     * ｛
     * "code":1
     * "msg":"操作成功"
     * ｝
     */
    @RequestMapping(value = "/zfb/notify")
    public String zfbNotify(HttpServletRequest request) {
        try {
            Map<String, String> map = XMLUtil.aliPayNotify(request);
            if (map.get("trade_status").equals("TRADE_SUCCESS")) {
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
     * 微信支付后回调方法
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/wcPay/notify")
    public String wcPayNotify(HttpServletRequest request) {
        try {
            Map<String, String> map = XMLUtil.wxPayNotify(request);
            // 验证签名
            if ("SUCCESS".equals(map.get("return_code"))) {
                //调起支付所传入的支付流水号
                String outTradeNo = map.get("out_trade_no");
                System.out.println("微信回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                pay(outTradeNo, "微信");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("---------------------------回调通知异常！！！-------------------------------");
        } finally {
            return XMLUtil.setWechatXml("SUCCESS", "OK");
        }
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

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("charge", "ch_1DEvhpBZlgk9Vyx2AQKo6ILr");
        //params.put("amount", 999);
        try {
            Refund refund = Refund.create(params);
            System.out.println(refund.getStatus());
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
