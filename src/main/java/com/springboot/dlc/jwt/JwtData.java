package com.springboot.dlc.jwt;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: liujiebang
 * @Description: JWT加密参数类
 * @Date: 2018/7/2 16:52
 **/
@Data
@PropertySource(value = "classpath:/config/jwt.properties")
@Component
public class JwtData {

    @Value("${audience.clientId}")
    private String clientId;
    @Value("${audience.base64Secret}")
    private String base64Secret;
    @Value("${audience.name}")
    private String name;
    @Value("${audience.expiresSecond}")
    private int expiresSecond;
}
