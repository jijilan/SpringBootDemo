package com.springboot.dlc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.WxAgent;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.IWxAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/wx-agent")
public class WxAgentController {

    @Autowired
    private IWxAgentService wxAgentService;
    @Value("${web.static-resource-path}")
    private String staticResourcePath;

    /**
     * 根据主键查询单个对象
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResultView get(@PathVariable String id) {
        WxAgent wxAgent = wxAgentService.getById(id);
        return ResultView.ok(wxAgent);
    }

    /**
     * 分页查询单个对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/find/{pageNo}/{pageSize}")
    public ResultView find(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        IPage iPage = new Page(pageNo, pageSize);
        IPage page = wxAgentService.page(iPage, null);
        return ResultView.ok(page);
    }

    /**
     * 分页查询多个多谢
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/findAgentAndRole/{pageNo}/{pageSize}")
    public ResultView findAgentAndRole(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        IPage page = wxAgentService.findAgentAndRole(pageNo, pageSize);
        return ResultView.ok(page);
    }
}
