package com.springboot.dlc.config;

import com.springboot.dlc.handler.BackAuthenticationInterceptor;
import com.springboot.dlc.handler.BackAuthorizationInterceptor;
import com.springboot.dlc.handler.FrontAuthenticationInterceptor;
import com.springboot.dlc.resources.InterceptorResource;
import com.springboot.dlc.resources.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WebResource webResource;
    @Autowired
    private InterceptorResource interceptorResource;

    /**
     * 拦截器注册
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(frontAuthenticationInterceptor())
                .addPathPatterns(interceptorResource.getFrontAuthenticationAddPathPatterns())
                .excludePathPatterns(interceptorResource.getFrontAuthenticationExcludePathPatterns());
        registry.addInterceptor(backAuthenticationInterceptor())
                .addPathPatterns(interceptorResource.getBackAuthenticationAddPathPatterns())
                .excludePathPatterns(interceptorResource.getBackAuthenticationExcludePathPatterns());
        registry.addInterceptor(backAuthorizationInterceptor())
                .addPathPatterns(interceptorResource.getBackAuthorizationAddPathPatterns())
                .excludePathPatterns(interceptorResource.getBackAuthorizationExcludePathPatterns());
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
                .addResourceLocations("file:" + webResource.getStaticResourcePath());
        super.addResourceHandlers(registry);
    }

    /**
     * 欢迎页请求重定向
     * --可用来处理微信公众平台或者开放平台获取code操作
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", webResource.getProjectPath() + webResource.getWelcomePath());
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
    FrontAuthenticationInterceptor frontAuthenticationInterceptor() {
        return new FrontAuthenticationInterceptor();
    }

    @Bean
    BackAuthenticationInterceptor backAuthenticationInterceptor() {
        return new BackAuthenticationInterceptor();
    }

    @Bean
    BackAuthorizationInterceptor backAuthorizationInterceptor() {
        return new BackAuthorizationInterceptor();
    }

}
