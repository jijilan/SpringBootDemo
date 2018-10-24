package com.springboot.dlc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @Author: liujiebang
 * @Description: 方法验证后置处理器:使方式当中校验注解生效，并且通过ConstraintViolationException 异常来捕获异常信息
 * @Date: 2018/6/27 15:05
 **/
@Configuration
public class ValidationFactoryConfig {


    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
