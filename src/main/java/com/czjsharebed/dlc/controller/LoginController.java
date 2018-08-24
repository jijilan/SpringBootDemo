package com.czjsharebed.dlc.controller;


import com.czjsharebed.dlc.jwt.JWTData;
import com.czjsharebed.dlc.jwt.JwtUtil;
import com.czjsharebed.dlc.model.SysManager;

import com.czjsharebed.dlc.redis.RedisService;
import com.czjsharebed.dlc.result.ResultEnum;

import com.czjsharebed.dlc.service.SysManagerService;

import com.czjsharebed.dlc.utils.*;


import com.czjsharebed.dlc.result.ResultStatus;
import com.czjsharebed.dlc.result.ResultView;
import com.github.liujiebang.pay.wx.config.WxRequest;
import com.github.liujiebang.pay.wx.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liujiebang
 * @Description: 登录
 * @Date: 2018/7/2 16:48
 **/
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private JWTData jwtData;
    @Autowired
    private RedisService redisService;

    @Autowired
    private SysManagerService sysManagerService;

    @Autowired
    private WxAuthService wxAuthService;
    /**
     * 后台登陆
     *
     * @param userAcount 账号
     * @param passWord   密码
     * @return
     */
    @PostMapping(value = "/backLogin")
    public ResultView backLogin(String userAcount, String passWord) {
        if (StringUtils.isBlank(userAcount)) {
            return ResultView.error(ResultEnum.CODE_14);
        }
        if (StringUtils.isBlank(passWord)) {
            return ResultView.error(ResultEnum.CODE_15);
        }
        SysManager sysManager= sysManagerService.backLogin(userAcount,DESCode.encode(passWord));
        if(sysManager!=null){
            Map<String, Object> map = new HashMap<>(2);
            String token = jwtToken(ResultStatus.MANAGER_ID, sysManager.getManagerId(), sysManager);
            if (StringUtils.isEmpty(token)) {
                return ResultView.error(ResultEnum.CODE_9999);
            }
            map.put(ResultStatus.TOKEN,token);
            map.put(ResultStatus.MANAGER,sysManager);
            return ResultView.ok(map);
        }

        return ResultView.error(ResultEnum.CODE_4);
    }

    /**
     * 获取手机验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @GetMapping("/getPhoneCode")
    public ResultView getPhoneCode(String phone, Integer type) {
        if (StringUtils.isEmpty(phone)) {
            return ResultView.error(ResultEnum.CODE_9);
        }
        if (type == null) {
            return ResultView.error(ResultEnum.CODE_16);
        }
        String phoneCode = IdentityUtil.getRandomNum(6);
        //1:完善用户信息  2:修改手机  3：验证新手机
        String messageModel = PhoneSMS.messageModel(phoneCode, type);
        if (PhoneSMS.sendSMS(messageModel, phone) == 1) {
            redisService.set(ResultStatus.CZJSHAREBED + phone, phoneCode, 180);
            log.info("给" + phone + "手机号码发送验证码----->" + phoneCode);
            return ResultView.ok();
        } else {
            return ResultView.error(ResultEnum.CODE_17);
        }
    }

    /**
     * 生成token
     *
     * @param unique   id标识
     * @param uniqueId 用户编号-或管理员编号
     * @param obj      用户对象或管理员对象
     * @return
     */
    private String jwtToken(String unique, String uniqueId, Object obj) {
        String jwtToken = JwtUtil.createJWT(unique,
                uniqueId,
                jwtData.getClientId(),
                jwtData.getName(),
                jwtData.getExpiresSecond(),
                jwtData.getBase64Secret());
        redisService.set(uniqueId, obj, ResultStatus.TONKEN_OUT_TIME);
        return jwtToken;
    }

}
