package com.springboot.dlc.handler;


import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.exception.PayException;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: liujiebang
 * @Description: Controller异常捕获类
 * @Date: 2018/7/2 16:51
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(MyException e){
        e.printStackTrace();
        return ResultView.error(e.getResultEnum());
    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(AuthException e){
        log.info("--------------------用户未登陆----------------------");
        return ResultView.error(e.getResultEnum());
    }

    @ExceptionHandler(value = PayException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(PayException e){
        e.printStackTrace();
        return ResultView.error(e.getResultEnum().getCode(),e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(RuntimeException e){
        e.printStackTrace();
        return ResultView.error(ResultEnum.CODE_500);
    }


}
