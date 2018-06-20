package com.dalian.dlc.controller.front;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/front")
public class FrontUsersController {

    @Value("${web.portrait-path}")
    private String portraitPath;


}
