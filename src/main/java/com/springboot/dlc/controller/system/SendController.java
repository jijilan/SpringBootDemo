package com.springboot.dlc.controller.system;


import com.springboot.dlc.controller.base.BaseController;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.utils.*;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @Author: liujiebang
 * @Description:
 * @Date: 2018/7/2 16:48
 **/
@Slf4j
@RestController
public class SendController extends BaseController {

    /**
     * 发送手机短信
     *
     * @param phone
     * @param type
     * @return
     */
    @GetMapping("/sendMessage")
    public ResultView getPhoneCode(@NotEmpty(message = "手机号码不能为空") String phone,
                                   @NotNull(message = "短信类型不能为空") Integer type) {
        String phoneCode = IdentityUtil.getRandomNum(6);
        String messageModel = PhoneSMS.messageModel(phoneCode, type);
        if (PhoneSMS.sendSMS(messageModel, phone) == 1) {
            redisService.set(ResultStatus.PROJECT_NAME + phone, phoneCode, 180);
            log.info("给" + phone + "手机号码发送验证码----->" + phoneCode);
            return ResultView.ok();
        } else {
            return ResultView.error(ResultEnum.CODE_17);
        }
    }

}
