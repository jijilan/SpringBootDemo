package com.dalian.dlc.config;

import com.dalian.dlc.handler.FrontLoginInterceptor;
import com.dalian.dlc.handler.BackLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Author: liujiebang
 * @Description: 过滤器与拦截器配置
 * @Date: 2018/6/27 15:05
 **/
@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Value("${web.portrait-path}")
    private String portraitPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtLoginInterceptor()).addPathPatterns("/front/**");
        registry.addInterceptor(sessionLoginInterceptor()).addPathPatterns("/back/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:"+portraitPath);
        super.addResourceHandlers(registry);
    }

    @Bean
    FrontLoginInterceptor jwtLoginInterceptor() {
        return new FrontLoginInterceptor();
    }

    @Bean
    BackLoginInterceptor sessionLoginInterceptor() {
        return new BackLoginInterceptor();
    }
}
