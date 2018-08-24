package com.czjsharebed.dlc.config;



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
    @Value("${wx_dpAppId}")
    private String wx_dpAppId;
    @Value("${wx_dpSecrect}")
    private String wx_dpSecrect;
    @Value("${wx_dpMchId}")
    private String wx_dpMchId;
    @Value("${wx_dpCertPath}")
    private String wx_dpCertPath;
    @Value("${wx_dpMchKey}")
    private String wx_dpMchKey;

    /**
     * 微信公众号
     */
    @Value("${wx_pnAppId}")
    private  String wx_pnAppId;
    @Value("${wx_pnMchId}")
    private  String wx_pnMchId;
    @Value("${wx_pnSecrect}")
    private  String wx_pnSecrect;
    @Value("${wx_pnMchKey}")
    private  String wx_pnMchKey;
    @Value("${wx_pnCertPath}")
    private String wx_pnCertPath;

    /**
     * 微信小程序
     */
    @Value("${wx_spAppId}")
    private  String wx_spAppId;
    @Value("${wx_spMchId}")
    private  String wx_spMchId;
    @Value("${wx_spSecrect}")
    private  String wx_spSecrect;
    @Value("${wx_spMchKey}")
    private  String wx_spMchKey;
    @Value("${wx_spCertPath}")
    private String wx_spCertPath;

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
        wxPayConfig.setWxOpAppId(wx_dpAppId);
        wxPayConfig.setWxOpSecrect(wx_dpSecrect);
        wxPayConfig.setWxOpMchId(wx_dpMchId);
        wxPayConfig.setWxOpMchKey(wx_dpMchKey);
        wxPayConfig.setWxOpCertPath(wx_dpCertPath);
        //公众号
        wxPayConfig.setWxPpAppId(wx_pnAppId);
        wxPayConfig.setWxPpSecrect(wx_pnSecrect);
        wxPayConfig.setWxPpMchId(wx_pnMchId);
        wxPayConfig.setWxPpMchKey(wx_pnMchKey);
        wxPayConfig.setWxPpCertPath(wx_pnCertPath);
        //小程序
        wxPayConfig.setWxSpAppId(wx_spAppId);
        wxPayConfig.setWxSpSecrect(wx_spSecrect);
        wxPayConfig.setWxSpMchId(wx_spMchId);
        wxPayConfig.setWxSpMchKey(wx_spMchKey);
        wxPayConfig.setWxSpCertPath(wx_spCertPath);
        //统一回调地址
        wxPayConfig.setNotifyUrl(wx_notifyUrl);
        return wxPayConfig;
    }
}
