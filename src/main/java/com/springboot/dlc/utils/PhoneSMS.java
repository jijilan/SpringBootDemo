package com.springboot.dlc.utils;

import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


@Slf4j
public class PhoneSMS {

    public static final String ZHUTONG_URL = "http://www.api.zthysms.com/sendSms.do";

    public static final String PHONE_BODY = "【XXX】验证码:";

    public static final int TIMEOUT = 180 / 60;


    /**
     * 助通短信
     *
     * @param messageContent
     * @param phone
     * @return
     */
    public static int sendSMS(String messageContent, String phone) {
        String dateTime = DateUtils.getCurrentDateTime("yyyyMMddHHmmss");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "lizhong888hy");
        params.add("password", MD5Util.MD5Encode(MD5Util.MD5Encode("XS4mI9", "utf-8") + dateTime, "utf-8"));
        params.add("tkey", dateTime);
        params.add("mobile", phone);
        params.add("content", messageContent);
        try {
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
            String account = restTemplate.postForObject(ZHUTONG_URL, httpEntity, String.class);
            log.info("获取短信短信平台返回信息:{}", account);
            if (account != null) {
                String[] strs = account.split(",");
                return Integer.valueOf(strs[0]);
            }
        } catch (Exception e) {
            throw new MyException(ResultEnum.CODE_1001);
        }
        return -1;
    }


    public static String messageModel(String mobile_code, int modelType) {
        StringBuffer sbf = new StringBuffer();
        switch (modelType) {
            case 1:
                sbf.append(PHONE_BODY + mobile_code + "(当前操作为注册,有效期为" + TIMEOUT + "分钟!)");
                break;
            case 2:
                sbf.append(PHONE_BODY + mobile_code + "(当前操作为登陆,有效期为" + TIMEOUT + "分钟!)");
                break;
            case 3:
                sbf.append(PHONE_BODY + mobile_code + "(当前操作为绑定手机,有效期为" + TIMEOUT + "分钟!)");
                break;
            case 4:
                sbf.append(PHONE_BODY + mobile_code + "(当前操作为提现申请,有效期为" + TIMEOUT + "分钟!)");
                break;
            case 5:
                sbf.append(PHONE_BODY + mobile_code + "(当前操作为修改手机号,有效期为" + TIMEOUT + "分钟!)");
                break;
            case 6:
                sbf.append(PHONE_BODY + mobile_code + "(当前操为密码修改,有效期为" + TIMEOUT + "分钟!)");
                break;
            default:
                throw new MyException(ResultEnum.CODE_16);
        }
        return sbf.toString();
    }

}
