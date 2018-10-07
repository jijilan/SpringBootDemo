package com.springboot.dlc.handler;


import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/8/14
 * @Description:
 **/
public class BackRoleInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisService redisService;

    /**
     * @Description 权限拦截
     * @Date 2018/8/20 21:35
     * @Author liangshihao
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求资源路径
        String requestURI = request.getRequestURI();
        //请求方式
        String method=request.getMethod();
        if("/sys/back/sysManager/getExitLogin".equals(requestURI)||
            "/sys/back/sysManager/updateManagerPwd".equals(requestURI)||
                "/sys/back/sysManager/managerInfo".equals(requestURI)||
                 "/sys/back/sysMenu/authority/getAuthorityMenuList".equals(requestURI)
                ){
                return  true;
        }
        String managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        SysManager manager = (SysManager) redisService.get(managerId);
        //超级管理员直接放行
        if (manager != null && manager.getManagerType() == 1) {
            return true;
        }
        Set<String> authorityList = (Set<String>) redisService.get(ResultStatus.PROJECT_NAME + managerId);
        if (authorityList.contains(requestURI)) {
            return true;
        } else {
            throw new AuthException(ResultEnum.CODE_21);
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
