package com.dalian.dlc.controller;


import com.dalian.dlc.jwt.JWTData;
import com.dalian.dlc.jwt.JwtUtil;
import com.dalian.dlc.model.Manager;
import com.dalian.dlc.model.Users;
import com.dalian.dlc.redis.RedisService;
import com.dalian.dlc.result.ResultEnum;

import com.dalian.dlc.result.ResultStatus;
import com.dalian.dlc.result.ResultView;
import com.dalian.dlc.utils.*;
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

    /**
     * APP登陆
     *
     * @param phoneNumber 手机号码
     * @param phoneCode   验证码
     * @param openId      第三方登陆传
     * @param loginType   (必传)登陆类型【1：微信  2：QQ  3：微博  4:普通登陆】
     * @return
     */
    @PostMapping(value = "/frontLogin")
    public ResultView frontLogin(String phoneNumber, String phoneCode, String openId, Integer loginType) {
        if (loginType == null) {
            return ResultView.error(ResultEnum.CODE_12);
        }
        if (loginType == ResultStatus.PHONE_LOGIN && StringUtils.isEmpty(phoneNumber)) {
            return ResultView.error(ResultEnum.CODE_9);
        }
        if (loginType == ResultStatus.PHONE_LOGIN && StringUtils.isEmpty(phoneCode)) {
            return ResultView.error(ResultEnum.CODE_10);
        }
        if ((loginType == ResultStatus.QQ_OPENID || loginType == ResultStatus.WX_OPENID
                || loginType == ResultStatus.WB_OPENID) && StringUtils.isEmpty(openId)) {
            return ResultView.error(ResultEnum.CODE_13);
        }
        String code = "123456";
        //还需判断redis当中的验证码是否过期！我这边先写死
        if (loginType == ResultStatus.PHONE_LOGIN && !code.equals(phoneCode)) {
            return ResultView.error(ResultEnum.CODE_11);
        }
        /**这部分逻辑需自己补充*/
        Users users=null;

        if (users != null){
            String jwtToken = jwtToken(ResultStatus.USER_ID,users.getUserId(),users);
            Map<String, Object> map = new HashMap<>();
            map.put(ResultStatus.TOKEN, jwtToken);
            map.put(ResultStatus.USER, users);
            return ResultView.ok(map);
        }
        /**后面的登录逻辑需自己补充*/
        return ResultView.error(ResultEnum.CODE_4);
    }

    /**
     * 后台登陆
     * @param loginAccount  账号
     * @param loginPassword 密码
     * @return
     */
    @PostMapping(value = "/backLogin")
    public ResultView backLogin(String loginAccount, String loginPassword) {
        if (StringUtils.isEmpty(loginAccount)) {
            return ResultView.error(ResultEnum.CODE_14);
        }
        if (StringUtils.isEmpty(loginPassword)) {
            return ResultView.error(ResultEnum.CODE_15);
        }
        /**这部分逻辑需自己补充*/
        Manager manager = null;
        if (null != manager) {
            String jwtToken = jwtToken(ResultStatus.MANAGER_ID,manager.getManagerId(),manager);
            Map<String, Object> map = new HashMap<>();
            map.put(ResultStatus.TOKEN, jwtToken);
            map.put(ResultStatus.MANAGER, manager);
            /**后面返回权限信息逻辑需自己补充*/
            return ResultView.ok(map);
        } else {
            return ResultView.error(ResultEnum.CODE_4);
        }
    }


    /**
     * 获取手机验证码
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
        String phoneCode = LotteryUtil.getRandomNum(6);
        //1:登录  2:绑定手机
        String messageModel = PhoneSMS.messageModel(phoneCode, type);
        if (PhoneSMS.sendSMS(messageModel, phone) == 1) {
            redisService.set(ResultStatus.DALIANPARKING + phone, phoneCode, 180);
            log.info("给" + phone + "手机号码发送验证码----->" + phoneCode);
            return ResultView.ok();
        } else {
            return ResultView.error(ResultEnum.CODE_17);
        }
    }

    /**
     * 生成token
     * @param unique id标识
     * @param uniqueId 用户编号-或管理员编号
     * @param obj 用户对象或管理员对象
     * @return
     */
    private String jwtToken(String unique,String uniqueId,Object obj){
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
