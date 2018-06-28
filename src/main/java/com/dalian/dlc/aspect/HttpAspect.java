package com.dalian.dlc.aspect;


import com.dalian.dlc.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;

@Aspect
@Component
@Slf4j
public class HttpAspect {
    @Autowired
    private GlobalExceptionHandler exceptionHandle;

    @Pointcut("execution(public * com.dalian.dlc.controller..*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        log.info("Url={}",request.getRequestURL());
        //method
        log.info("Method={}",request.getMethod());
        //class_method
        log.info("Controller={}",joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            log.info("Parameter:"+(i+1)+"=:"+joinPoint.getArgs()[i]);
        }
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result = null;
        try {

        } catch (RuntimeException e){
            return exceptionHandle.defaultErrorHandler(e);
        }
        if(result == null){
            return proceedingJoinPoint.proceed();
        }else {
            return result;
        }
    }

    @AfterReturning(pointcut = "log()",returning = "object")//打印输出结果
    public void doAfterReturing(Object object){
        log.info("response={}",object.toString());
    }
}
