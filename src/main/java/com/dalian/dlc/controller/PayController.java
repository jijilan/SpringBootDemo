package com.dalian.dlc.controller;



import com.dalian.dlc.pay.wx.tool.XMLUtil;
import com.dalian.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
     * @param request
     * @param outTradeNo 支付流水号
     * @param payType    支付方式1：余额支付 2.微信支付 3.支付宝支付
     * @return
     */
    @PostMapping(value = "/front/pay")
    public ResultView pay(HttpServletRequest request, String outTradeNo, Integer payType) {
        return ResultView.ok();
    }


    //支付宝支付后回调方法

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
    public void zfbNotify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            if (params.get("trade_status").equals("TRADE_SUCCESS")) {

                String outTradeNo = params.get("out_trade_no");
                System.out.println("支付宝回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                pay(outTradeNo,null);
                log.info("---------------------------支付宝回调-------------------------------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付后回调方法
     * @param request
     * @return
     */
    @PostMapping(value = "/wcPay/notify")
    public String wcPayNotify(HttpServletRequest request) {
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            log.info("回调xml【" + result + "】");
            Map<String, String> map = XMLUtil.doXMLParse(result);
            // 验证签名
            if ("SUCCESS".equals(map.get("return_code"))) {

                //调起支付所传入的支付流水号
                String outTradeNo = map.get("out_trade_no");
                System.out.println("微信回调订单号－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－" + outTradeNo);
                pay(outTradeNo,null);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("---------------------------回调通知异常！！！-------------------------------");
        }finally {
            return  XMLUtil.setXml("SUCCESS", "OK");
        }
    }


    /**
     * 支付回调通用业务逻辑
     * @param outTradeNo 支付流水号
     * @param payType 支付类型
     */
    private void pay(String outTradeNo,Integer payType){
        String sub = outTradeNo.substring(0, 3);

    }
}
