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
 * @Author: liujiebang
 * @Description: 微信支付授权,支付宝支付配置类
 * @Date: 2018/6/27 15:05
 **/
@Data
@PropertySource(value = "classpath:/config/pay.properties")
@Configuration
public class PayConfig {
    /**
     * 微信APP
     */
    @Value("${wx_OpAppId}")
    private String wxOpAppId;
    @Value("${wx_OpSecrect}")
    private String wxOpSecrect;
    @Value("${wx_OpMchId}")
    private String wxOpMchId;
    @Value("${wx_OpCertPath}")
    private String wxOpCertPath;
    @Value("${wx_OpMchKey}")
    private String wxOpMchKey;

    /**
     * 微信公众号
     */
    @Value("${wx_PpAppId}")
    private String wxPpAppId;
    @Value("${wx_PpMchId}")
    private String wxPpMchId;
    @Value("${wx_PpSecrect}")
    private String wxPpSecrect;
    @Value("${wx_PpMchKey}")
    private String wxPpMchKey;
    @Value("${wx_PpCertPath}")
    private String wxPpCertPath;

    /**
     * 微信小程序
     */
    @Value("${wx_SpAppId}")
    private String wxSpAppId;
    @Value("${wx_SpMchId}")
    private String wxSpMchId;
    @Value("${wx_SpSecrect}")
    private String wxSpSecrect;
    @Value("${wx_SpMchKey}")
    private String wxSpMchKey;
    @Value("${wx_SpCertPath}")
    private String wxSpCertPath;

    /**
     * 微信统一回调地址
     */
    @Value("${wx_notifyUrl}")
    private String wxNotifyUrl;

    /**
     * 支付宝
     */
    @Value("${ali_appId}")
    private String aliAppId;
    @Value("${ali_private_key}")
    private String aliPrivateKey;
    @Value("${ali_public_key}")
    private String aliPublicKey;
    @Value("${ali_notifyUrl}")
    private String aliNotifyUrl;
    @Value("${ali_seller_id}")
    private String aliSellerId;
    @Value("${ali_returnUrl}")
    private String aliReturnUrl;


    @Bean
    public AliPayService aliPayService() {
        AliPayService aliPayService = new AliPayServiceImpl();
        aliPayService.setAliPayConfigStorage(aliPayConfig());
        return aliPayService;
    }

    @Bean
    public AliPayConfig aliPayConfig() {
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(aliAppId);
        aliPayConfig.setSellerId(aliSellerId);
        aliPayConfig.setPrivateKey(aliPrivateKey);
        aliPayConfig.setAlipayPublicKey(aliPublicKey);
        aliPayConfig.setNotifyUrl(aliNotifyUrl);
        aliPayConfig.setReturnUrl(aliReturnUrl);
        return aliPayConfig;
    }


    @Bean
    public WxPayService wxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setWxConfigStorage(wxConfig());
        return wxPayService;
    }

    @Bean
    public WxAuthService wxAuthService() {
        WxAuthService wxAuthService = new WxAuthServiceImpl();
        wxAuthService.setWxConfigStorage(wxConfig());
        return wxAuthService;
    }

    @Bean
    public WxConfig wxConfig() {
        WxConfig wxPayConfig = new WxConfig();
        //APP
        wxPayConfig.setWxOpAppId(wxOpAppId);
        wxPayConfig.setWxOpSecrect(wxOpSecrect);
        wxPayConfig.setWxOpMchId(wxOpMchId);
        wxPayConfig.setWxOpMchKey(wxOpMchKey);
        wxPayConfig.setWxOpCertPath(wxOpCertPath);
        //公众号
        wxPayConfig.setWxPpAppId(wxPpAppId);
        wxPayConfig.setWxPpSecrect(wxPpSecrect);
        wxPayConfig.setWxPpMchId(wxPpMchId);
        wxPayConfig.setWxPpMchKey(wxPpMchKey);
        wxPayConfig.setWxPpCertPath(wxPpCertPath);
        //小程序
        wxPayConfig.setWxSpAppId(wxSpAppId);
        wxPayConfig.setWxSpSecrect(wxSpSecrect);
        wxPayConfig.setWxSpMchId(wxSpMchId);
        wxPayConfig.setWxSpMchKey(wxSpMchKey);
        wxPayConfig.setWxSpCertPath(wxSpCertPath);
        //统一回调地址
        wxPayConfig.setNotifyUrl(wxNotifyUrl);
        return wxPayConfig;
    }
}
