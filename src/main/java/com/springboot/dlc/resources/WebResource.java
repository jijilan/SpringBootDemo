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
public class WebResource {

    @Value("${web.static-resource-path}")
    private String staticResourcePath;

    @Value("${web.welcome-path}")
    private String welcomePath;

    @Value("${web.project-path}")
    private String projectPath;

    @Value("${web.equipment-path}")
    private String equipmentPath;
}
