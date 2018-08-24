package com.czjsharebed.dlc.handler;

import com.alibaba.druid.util.StringUtils;
import com.czjsharebed.dlc.exception.AuthException;
import com.czjsharebed.dlc.jwt.JwtUtil;
import com.czjsharebed.dlc.model.WxUser;
import com.czjsharebed.dlc.result.ResultEnum;
import com.czjsharebed.dlc.jwt.JWTData;
import com.czjsharebed.dlc.redis.RedisService;
import com.czjsharebed.dlc.result.ResultStatus;
import com.czjsharebed.dlc.service.WxUserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
public class FrontLoginInterceptor implements HandlerInterceptor{

    @Autowired
    private JWTData jwtData;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WxUserService wxUserService;
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
                String usersId = (String) claims.get(ResultStatus.USER_ID);
                WxUser users = (WxUser) redisService.get(usersId);
                if(users.getIsFlag()==ResultStatus.WXUSER_ISFLAG_2){
                    throw new AuthException(ResultEnum.CODE_154);
                }
                if (users != null) {
                    request.setAttribute(ResultStatus.USER_ID, usersId);
                    return true;
                }


            } catch (NullPointerException e) {
                throw new AuthException(ResultEnum.CODE_403);
            }catch (ClassCastException e){
                throw new AuthException(ResultEnum.CODE_403);
            }catch (Exception e){
                throw new AuthException(ResultEnum.CODE_403);
            }
        }
        throw new AuthException(ResultEnum.CODE_403);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
