package com.springboot.dlc.handler;


import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.exception.PayException;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: liujiebang
 * @Description: Controller异常捕获类
 * @Date: 2018/7/2 16:51
 **/
@RestControllerAdvice
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


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(BindException e){
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(ResultEnum.CODE_1000.getMsg())
                .append(fieldError.getField()).append("=[")
                .append(fieldError.getRejectedValue()).append("],")
                .append(fieldError.getDefaultMessage());
        return ResultView.error(ResultEnum.CODE_1000.getCode(),sb.toString());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultView defaultErrorHandler(Exception e){
        e.printStackTrace();
        return ResultView.error(ResultEnum.CODE_500);
    }


}
