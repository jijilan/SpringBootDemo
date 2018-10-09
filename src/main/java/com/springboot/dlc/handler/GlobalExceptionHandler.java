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

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(MyException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResultView.error(e.getResultEnum());
    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(AuthException e) {
        // 记录错误信息
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(e.getResultEnum());
    }

    @ExceptionHandler(value = PayException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(PayException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(e.getResultEnum().getCode(), e.getMessage());
    }


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(BindException e) {
        log.error(ExceptionUtils.getMessage(e));
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(ResultEnum.CODE_2.getMsg()).append(fieldError.getDefaultMessage());
        return ResultView.error(ResultEnum.CODE_2.getCode(), sb.toString());
    }


    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResultView requestTypeMismatch(TypeMismatchException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_400.getCode(), "参数类型不匹配,参数" + e.getPropertyName() + "类型应该为" + e.getRequiredType());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(MissingServletRequestParameterException e) {
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_400.getCode(), "缺少必要参数,参数名称为" + e.getParameterName());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultView defaultErrorHandler(HttpRequestMethodNotSupportedException e){
        log.error(ExceptionUtils.getMessage(e));
        return ResultView.error(ResultEnum.CODE_405);
    }

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

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultView defaultErrorHandler(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResultView.error(ResultEnum.CODE_500);
    }


}
