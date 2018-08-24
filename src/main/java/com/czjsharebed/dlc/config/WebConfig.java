package com.czjsharebed.dlc.config;

import com.czjsharebed.dlc.handler.BackLoginInterceptor;
import com.czjsharebed.dlc.handler.FrontLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${web.portrait-path}")
    private String portraitPath;

    @Value("${web.welcome-path}")
    private String welcomePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtLoginInterceptor()).addPathPatterns("/front/**");
        registry.addInterceptor(sessionLoginInterceptor()).addPathPatterns("/back/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + portraitPath);
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", welcomePath);
        super.addViewControllers(registry);
    }


    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
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
