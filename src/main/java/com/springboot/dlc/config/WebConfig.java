package com.springboot.dlc.config;

import com.springboot.dlc.handler.BackLoginInterceptor;
import com.springboot.dlc.handler.FrontLoginInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: liujiebang
 * @Description: 过滤器与拦截器配置
 * @Date: 2018/6/27 15:05
 **/
@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${web.static-resource-path}")
    private String staticResourcePath;

    @Value("${web.welcome-path}")
    private String welcomePath;

    @Value("${web.project-path}")
    private String projectPath;

    /**
     * 拦截器注册
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(frontLoginInterceptor()).addPathPatterns("/front/**");
        registry.addInterceptor(backLoginInterceptor()).addPathPatterns("/back/**");
        super.addInterceptors(registry);
    }

    /**
     * 静态资源处理
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + staticResourcePath);
        super.addResourceHandlers(registry);
    }

    /**
     * 请求重定向
     * --可用来处理微信公众平台或者开放平台获取code操作
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", projectPath + welcomePath);
        super.addViewControllers(registry);
    }

    /**
     * 设置HTTP请求报文字符串UTF-8编码格式
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Bean
    HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    FrontLoginInterceptor frontLoginInterceptor() {
        return new FrontLoginInterceptor();
    }

    @Bean
    BackLoginInterceptor backLoginInterceptor() {
        return new BackLoginInterceptor();
    }

}
