package com.springboot.dlc.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/16
 * @Description:
 **/
@Getter
@Setter
@Component
public class InterceptorResource {

    @Value("${interceptor.front-authentication.addPathPatterns}")
    private String frontAuthenticationAddPathPatterns;
    @Value("#{'${interceptor.front-authentication.excludePathPatterns}'.split(',')}")
    private String [] frontAuthenticationExcludePathPatterns;

    @Value("${interceptor.back-authentication.addPathPatterns}")
    private String backAuthenticationAddPathPatterns;
    @Value("#{'${interceptor.back-authentication.excludePathPatterns}'.split(',')}")
    private String [] backAuthenticationExcludePathPatterns;

    @Value("${interceptor.back-authorization.addPathPatterns}")
    private String backAuthorizationAddPathPatterns;
    @Value("#{'${interceptor.back-authorization.excludePathPatterns}'.split(',')}")
    private String [] backAuthorizationExcludePathPatterns;
}
