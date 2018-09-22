package com.springboot.dlc.controller.system;


import com.springboot.dlc.jwt.JwtData;
import com.springboot.dlc.jwt.JwtUtil;

import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.result.ResultEnum;


import com.springboot.dlc.utils.*;


import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: liujiebang
 * @Description: 登录
 * @Date: 2018/7/2 16:48
 **/
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private JwtData jwtData;
    @Autowired
    private RedisService redisService;


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
