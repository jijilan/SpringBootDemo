package com.springboot.dlc.handler;

import com.alibaba.druid.util.StringUtils;
import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.jwt.JwtUtil;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.jwt.JwtData;
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
 * @Description: 前端拦截器
 * @Date: 2018/7/2 16:50
 **/
@Slf4j
public class FrontLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtData jwtData;
    @Autowired
    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(ResultStatus.TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new AuthException(ResultEnum.CODE_3);
        } else {

            try {
                String userId = JwtUtil.getUniqueId(request, jwtData, token, ResultStatus.USER_ID);
                /**前端授权拦截业务逻辑部分*/
                //throw new AuthException(ResultEnum.CODE_21);

                request.setAttribute(ResultStatus.USER_ID, userId);
            } catch (NullPointerException e) {
                throw new AuthException(ResultEnum.CODE_403);
            } catch (ClassCastException e) {
                throw new AuthException(ResultEnum.CODE_403);
            } /*catch (AuthException e) {
                throw new AuthException(ResultEnum.CODE_21);
            }*/ catch (Exception e) {
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
