package com.springboot.dlc.handler;


import com.springboot.dlc.exception.AuthException;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.exception.PayException;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;


/**
 * @Author: liujiebang
 * @Description: Controller异常捕获类
 * @Date: 2018/7/2 16:51
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResultView myExceptionHandler(MyException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResultView.error(e.getResultEnum());
    }

    /**
     * 授权异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public ResultView authExceptionHandler(AuthException e) {
        // 记录错误信息
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(e.getResultEnum());
    }

    /**
     * 支付异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = PayException.class)
    @ResponseBody
    public ResultView payExceptionHandler(PayException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(e.getResultEnum().getCode(), e.getMessage());
    }

    /**
     * 获取bindingResult中的错误信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultView bindExceptionHandler(BindException e) {
        log.error(ExceptionUtils.getMessage(e));
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        String errorMsg = fieldError.getDefaultMessage();
        sb.append(ResultEnum.CODE_2.getMsg()).append(errorMsg);
        return ResultView.error(ResultEnum.CODE_2.getCode(), sb.toString());
    }


    /**
     * 请求参数类型不匹配
     *
     * @param e
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResultView typeMismatchExceptionHandler(TypeMismatchException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_400.getCode(), "参数类型不匹配,参数" + e.getPropertyName() + "类型应该为" + e.getRequiredType());
    }

    /**
     * 请求参数个数不匹配
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultView missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_400.getCode(), "缺少必要参数,参数名称为" + e.getParameterName());
    }

    /**
     * 请求方式错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultView httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_405);
    }

    /**
     * Violation 参数校验异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultView constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(ExceptionUtils.getMessage(e));
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        StringBuffer sb = new StringBuffer(ResultEnum.CODE_2.getMsg());
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            sb.append(",");
            sb.append(cvl.getMessageTemplate());
            break;
        }
        return ResultView.error(ResultEnum.CODE_2.getCode(), sb.toString());
    }

    /**
     * redis 缓存连接异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = JedisConnectionException.class)
    @ResponseBody
    public ResultView redisConnectionFailureExceptionHandler(JedisConnectionException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResultView.error(ResultEnum.CODE_1015);
    }

    /**
     * 默认异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultView defaultErrorHandler(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResultView.error(ResultEnum.CODE_500);
    }


}
