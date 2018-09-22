package com.springboot.dlc.config;



import com.github.liujiebang.pay.ali.config.AliPayConfig;
import com.github.liujiebang.pay.ali.service.AliPayService;
import com.github.liujiebang.pay.ali.service.impl.AliPayServiceImpl;
import com.github.liujiebang.pay.wx.config.WxConfig;
import com.github.liujiebang.pay.wx.service.WxAuthService;
import com.github.liujiebang.pay.wx.service.WxPayService;
import com.github.liujiebang.pay.wx.service.impl.WxAuthServiceImpl;
import com.github.liujiebang.pay.wx.service.impl.WxPayServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 支付参数
 */
@Data
@PropertySource(value = "classpath:/config/pay.properties")
@Configuration
public class PayConfig {
    /**
     * 微信APP
     */
    @Value("${wx_OpAppId}")
    private String wx_OpAppId;
    @Value("${wx_OpSecrect}")
    private String wx_OpSecrect;
    @Value("${wx_OpMchId}")
    private String wx_OpMchId;
    @Value("${wx_OpCertPath}")
    private String wx_OpCertPath;
    @Value("${wx_OpMchKey}")
    private String wx_OpMchKey;

    /**
     * 微信公众号
     */
    @Value("${wx_PpAppId}")
    private  String wx_PpAppId;
    @Value("${wx_PpMchId}")
    private  String wx_PpMchId;
    @Value("${wx_PpSecrect}")
    private  String wx_PpSecrect;
    @Value("${wx_PpMchKey}")
    private  String wx_PpMchKey;
    @Value("${wx_PpCertPath}")
    private String wx_PpCertPath;

    /**
     * 微信小程序
     */
    @Value("${wx_SpAppId}")
    private  String wx_SpAppId;
    @Value("${wx_SpMchId}")
    private  String wx_SpMchId;
    @Value("${wx_SpSecrect}")
    private  String wx_SpSecrect;
    @Value("${wx_SpMchKey}")
    private  String wx_SpMchKey;
    @Value("${wx_SpCertPath}")
    private String wx_SpCertPath;

    /**
     * 微信统一回调地址
     */
    @Value("${wx_notifyUrl}")
    private String wx_notifyUrl;

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


    @Bean
    public AliPayService aliPayService(){
        AliPayService aliPayService=new AliPayServiceImpl();
        aliPayService.setAliPayConfigStorage(aliPayConfig());
        return aliPayService;
    }

    @Bean
    public AliPayConfig aliPayConfig(){
        AliPayConfig aliPayConfig=new AliPayConfig();
        aliPayConfig.setAppId(zfb_appId);
        aliPayConfig.setSellerId(zfb_seller_id);
        aliPayConfig.setPrivateKey(zfb_private_key);
        aliPayConfig.setPublicKey(zfb_public_key);
        aliPayConfig.setPrivateKey_user(zfb_private_key_user);
        aliPayConfig.setPublicKey_user(zfb_public_key_user);
        aliPayConfig.setNotifyUrl(zfb_notifyUrl);
        aliPayConfig.setReturnUrl(zfb_returnUrl);
        return aliPayConfig;
    }


    @Bean
    public WxPayService wxPayService(){
        WxPayService wxPayService=new WxPayServiceImpl();
        wxPayService.setWxConfigStorage(wxConfig());
        return wxPayService;
    }

    @Bean
    public WxAuthService wxAuthService(){
        WxAuthService wxAuthService=new WxAuthServiceImpl();
        wxAuthService.setWxConfigStorage(wxConfig());
        return wxAuthService;
    }
    @Bean
    public WxConfig wxConfig(){
        WxConfig wxPayConfig=new WxConfig();
        //APP
        wxPayConfig.setWxOpAppId(wx_OpAppId);
        wxPayConfig.setWxOpSecrect(wx_OpSecrect);
        wxPayConfig.setWxOpMchId(wx_OpMchId);
        wxPayConfig.setWxOpMchKey(wx_OpMchKey);
        wxPayConfig.setWxOpCertPath(wx_OpCertPath);
        //公众号
        wxPayConfig.setWxPpAppId(wx_PpAppId);
        wxPayConfig.setWxPpSecrect(wx_PpSecrect);
        wxPayConfig.setWxPpMchId(wx_PpMchId);
        wxPayConfig.setWxPpMchKey(wx_PpMchKey);
        wxPayConfig.setWxPpCertPath(wx_PpCertPath);
        //小程序
        wxPayConfig.setWxSpAppId(wx_SpAppId);
        wxPayConfig.setWxSpSecrect(wx_SpSecrect);
        wxPayConfig.setWxSpMchId(wx_SpMchId);
        wxPayConfig.setWxSpMchKey(wx_SpMchKey);
        wxPayConfig.setWxSpCertPath(wx_SpCertPath);
        //统一回调地址
        wxPayConfig.setNotifyUrl(wx_notifyUrl);
        return wxPayConfig;
    }
}
