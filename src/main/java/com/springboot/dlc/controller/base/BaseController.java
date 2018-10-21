package com.springboot.dlc.controller.base;

import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.jwt.JwtData;
import com.springboot.dlc.jwt.JwtUtil;
import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.resources.WebResource;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/16
 * @Description:
 **/
public class BaseController {

    @Autowired
    protected JwtData jwtData;
    @Autowired
    protected RedisService redisService;

    @Autowired
    private WebResource webResource;

    /**
     * 生成token
     *
     * @param unique   id标识
     * @param uniqueId 用户编号-或管理员编号
     * @param obj      用户对象或管理员对象
     * @return
     */
    protected String jwtToken(String unique, String uniqueId, Object obj) {
        String jwtToken = JwtUtil.createJWT(unique,
                uniqueId,
                jwtData.getClientId(),
                jwtData.getName(),
                jwtData.getExpiresSecond(),
                jwtData.getBase64Secret());
            redisService.set(ResultStatus.PROJECT_NAME + uniqueId, obj, ResultStatus.TONKEN_OUT_TIME);
        return jwtToken;
    }
}
