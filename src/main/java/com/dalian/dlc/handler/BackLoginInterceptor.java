package com.dalian.dlc.handler;

import com.alibaba.druid.util.StringUtils;
import com.dalian.dlc.exception.AuthException;
import com.dalian.dlc.jwt.JWTData;
import com.dalian.dlc.jwt.JwtUtil;
import com.dalian.dlc.model.Manager;
import com.dalian.dlc.redis.RedisService;
import com.dalian.dlc.result.ResultEnum;
import com.dalian.dlc.result.ResultStatus;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTData jwtData;
    @Autowired
    private RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //controller方法调用之前
        String token = request.getHeader(ResultStatus.TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new AuthException(ResultEnum.CODE_3);
        } else {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            jwtData = factory.getBean(JWTData.class);
            final Claims claims = JwtUtil.parseJWT(token, jwtData.getBase64Secret());
            try {
                String managerId = (String) claims.get(ResultStatus.MANAGER_ID);
                Manager manager = (Manager) redisService.get(managerId);
                if (manager != null) {
                    request.setAttribute(ResultStatus.MANAGER_ID, manager.getManagerId());
                } else {
                    throw new AuthException(ResultEnum.CODE_403);
                }
            } catch (NullPointerException e) {
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
