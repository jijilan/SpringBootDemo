package com.springboot.dlc.controller.system;

import com.github.liujiebang.pay.wx.entity.WxOAuth2Info;
import com.github.liujiebang.pay.wx.entity.WxUserInfo;
import com.github.liujiebang.pay.wx.service.WxAuthService;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/8/27
 * @Description:
 **/
@RequestMapping(value = "/wechat")
@RestController
@Slf4j
public class WeChatController {

    @Autowired
    private WxAuthService wxAuthService;

    @Value("${web.project-path}")
    protected String projectPath;
    /**
     * 公众号授权授权
     *
     * @param resultUrl
     * @return
     */
    @GetMapping("/publicPlatFormAuth")
    public String ppAuth(String resultUrl) {
        String redirectUrl = projectPath+"publicPlatFormUserInfo";
        return "redirect:" + wxAuthService.wxOpOAuth2CodeAuthorizationUrl(redirectUrl, resultUrl);
    }

    /**
     * 公众号获取用户信息
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/publicPlatFormUserInfo")
    public String jsapiUserInfo(String code, String state) {
        try {
            WxOAuth2Info wxOAuth2Info = wxAuthService.wxPpOAuth2AccessToken(code);
            WxUserInfo wxUserInfo = wxAuthService.wxOAuth2getUserInfo(wxOAuth2Info.getAccess_token(), wxOAuth2Info.getOpenid());
            log.info("获取userInfo相关信息{}", wxUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + state;
    }

    /**
     * WEB授权
     *
     * @param resultUrl
     * @return
     */
    @GetMapping("/openPlatFormAuth")
    public String openAuth(String resultUrl) {
        String redirectUrl = projectPath+"openPlatFormUserInfo";
        return "redirect:" + wxAuthService.wxOpOAuth2CodeAuthorizationUrl(redirectUrl, resultUrl);
    }

    /**
     * WEB获取用户信息
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/openPlatFormUserInfo")
    public String openUserInfo(String code, String state) {
        try {
            WxOAuth2Info wxOAuth2Info = wxAuthService.wxOpOAuth2AccessToken(code);
            WxUserInfo wxUserInfo = wxAuthService.wxOAuth2getUserInfo(wxOAuth2Info.getAccess_token(), wxOAuth2Info.getOpenid());
            log.info("获取userInfo相关信息{}", wxUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + state;
    }

    /**
     * 小程序获取OpenId
     *
     * @param code
     * @return
     */
    @GetMapping("/smallProgramAccessToken")
    @ResponseBody
    public ResultView smallProgramAccessToken(String code) {
        WxOAuth2Info wxOAuth2Info = null;
        try {
            wxOAuth2Info = wxAuthService.wxSpOAuth2AccessToken(code);
            log.info("获取openId相关信息---------{}", wxOAuth2Info.getOpenid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultView.ok(wxOAuth2Info.getOpenid());
    }
}
