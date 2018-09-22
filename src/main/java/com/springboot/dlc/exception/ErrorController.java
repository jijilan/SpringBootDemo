package com.springboot.dlc.exception;

import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @Author: liujiebang
 * @Description: 非Controller异常restFul风格处理
 * @Date: 2018/7/2 16:49
 **/
@Slf4j
@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public ResultView handleError(HttpServletResponse response,HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info("请求错误:{}",response.getStatus());
        log.info("请求IP是:{}",ip);
        if (response.getStatus() == ResultEnum.CODE_401.getCode()){
            return ResultView.error(ResultEnum.CODE_401);
        }else if (response.getStatus()== ResultEnum.CODE_403.getCode()){
            return ResultView.error(ResultEnum.CODE_403);
        }else if (response.getStatus()== ResultEnum.CODE_404.getCode()){
            return ResultView.error(ResultEnum.CODE_404);
        }else if (response.getStatus()== ResultEnum.CODE_405.getCode()){
            return ResultView.error(ResultEnum.CODE_405);
        }else if (response.getStatus()== ResultEnum.CODE_415.getCode()){
            return ResultView.error(ResultEnum.CODE_415);
        }
        return ResultView.error(ResultEnum.CODE_9999);
    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
