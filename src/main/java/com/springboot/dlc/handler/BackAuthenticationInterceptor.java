package com.springboot.dlc.handler;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.jwt.JwtData;
import com.springboot.dlc.jwt.JwtUtil;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.result.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liujiebang
 * @Description: 后台认证
 * @Date: 2018/7/2 16:50
 **/
@Slf4j
public class BackAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtData jwtData;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthException {
        String token = request.getHeader(ResultStatus.TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new AuthException(ResultEnum.CODE_3);
        } else {
            try {
                String managerId = JwtUtil.getUniqueId(request, jwtData, token, ResultStatus.MANAGER_ID);
                /**后台授权拦截业务逻辑部分*/
                SysManager sysManager = (SysManager) redisService.get(ResultStatus.PROJECT_NAME + managerId);
                if (sysManager.getIsFlag() == 2) {
                    throw new AuthException(ResultEnum.CODE_9);
                }
                request.setAttribute(ResultStatus.MANAGER_ID, managerId);
            } catch (NullPointerException e) {
                throw new AuthException(ResultEnum.CODE_403);
            } catch (ClassCastException e) {
                throw new AuthException(ResultEnum.CODE_403);
            } catch (AuthException e) {
                throw new AuthException(ResultEnum.CODE_9);
            } catch (Exception e) {
                throw new AuthException(ResultEnum.CODE_403);
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
