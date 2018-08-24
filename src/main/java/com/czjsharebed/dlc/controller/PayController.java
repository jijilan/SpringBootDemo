package com.czjsharebed.dlc.controller;




import com.github.liujiebang.pay.utils.XMLUtil;
import com.czjsharebed.dlc.result.ResultStatus;
import com.czjsharebed.dlc.result.ResultView;
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
    public ResultView pay(HttpServletRequest request,String outTradeNo,String payType) {
        String userId = (String) request.getAttribute(ResultStatus.USER_ID);
        Map map = null;

        return ResultView.ok(map);
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
            Map<String, String> map = XMLUtil.AliPayNotify(request);
            if (map.get("trade_status").equals("TRADE_SUCCESS")) {
                String outTradeNo = map.get("out_trade_no");
                System.out.println("支付宝回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                pay(outTradeNo,"支付宝");
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
                pay(outTradeNo,"微信");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("---------------------------回调通知异常！！！-------------------------------");
        } finally {
            return XMLUtil.setXml("SUCCESS", "OK");
        }
    }


    /**
     * 支付回调通用业务逻辑
     * @param outTradeNo 支付流水号
     * @param payType 支付类型
     */
    private void pay(String outTradeNo,String payType){

        String sub = outTradeNo.substring(0, 3);
    }
}
