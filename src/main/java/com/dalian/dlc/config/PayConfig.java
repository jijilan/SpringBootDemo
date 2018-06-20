package com.dalian.dlc.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 支付参数
 */
@Data
@PropertySource(value = "classpath:/propertiess/pay.properties")
@Component
public class PayConfig {
    /**
     * 微信APP
     */
    @Value("${wx_notifyUrl}")
    private String wx_notifyUrl;
    @Value("${wx_appId}")
    private String wx_appId;
    @Value("${wx_appSecrect}")
    private String wx_appSecrect;
    @Value("${wx_mchId}")
    private String wx_mchId;
    @Value("${wx_appMiYao}")
    private String wx_appMiYao;
    @Value("${wx_certPath}")
    private String wx_certPath;
    @Value("${wx_key}")
    private String wx_key;

    /**
     * 微信公众号
     */
    @Value("${gzh_appId}")
    private  String gzh_appId;
    @Value("${gzh_mchId}")
    private  String gzh_mchId;
    @Value("${gzh_appSecrect}")
    private  String gzh_appSecrect;
    @Value("${gzh_certPath_user}")
    private  String gzh_certPath_user;
    @Value("${gzh_key}")
    private String gzh_key;

    /**
     * 支付宝
     */
    @Value("${zfb_appId}")
    private  String zfb_appId;
    @Value("${zfb_private_key}")
    private  String zfb_private_key;
    @Value("${zfb_public_key}")
    private  String zfb_public_key;
    @Value("${zfb_sign_type}")
    private  String zfb_sign_type;
    @Value("${zfb_notifyUrl}")
    private  String zfb_notifyUrl;
    @Value("${zfb_gateway}")
    private  String zfb_gateway;
    @Value("${zfb_seller_id}")
    private  String zfb_seller_id;
    @Value("${zfb_charset}")
    private  String zfb_charset;
    @Value("${zfb_Object}")
    private  String zfb_Object;
    @Value("${zfb_returnUrl}")
    private  String zfb_returnUrl;
    @Value("${zfb_appId_user}")
    private  String zfb_appId_user;
    @Value("${zfb_private_key_user}")
    private  String zfb_private_key_user;
    @Value("${zfb_public_key_user}")
    private  String zfb_public_key_user;

}
