package com.springboot.dlc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.WxAgent;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.IWxAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liujiebang
 * @since 2018-09-22
 */
@Slf4j
@RestController
@RequestMapping("/front/wx-agent")
public class WxAgentController {

    @Autowired
    private IWxAgentService wxAgentService;
    @Value("${web.static-resource-path}")
    private String staticResourcePath;

    @GetMapping("/get/{id}")
    public ResultView get(@PathVariable String id) {
        WxAgent wxAgent = wxAgentService.getById(id);
        return ResultView.ok(wxAgent);
    }

    @GetMapping("/find")
    public ResultView find() {
        IPage iPage = new Page(1, 10);
        IPage page = wxAgentService.page(iPage, null);
        return ResultView.ok(page);
    }


    @GetMapping("/findAgentAndRole")
    public ResultView findAgentAndRole(Integer pageNo,Integer pageSize){
        IPage page = wxAgentService.findAgentAndRole(pageNo,pageSize);
        return ResultView.ok(page);
    }
}
