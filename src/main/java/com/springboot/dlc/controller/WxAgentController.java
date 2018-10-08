package com.springboot.dlc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.WxAgent;
import com.springboot.dlc.model.QPage;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.IWxAgentService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class WxAgentController {

    @Autowired
    private IWxAgentService wxAgentService;

    /**
     * 根据主键查询单个对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultView get(@PathVariable String id) {
        WxAgent wxAgent = wxAgentService.getById(id);
        return ResultView.ok(wxAgent);
    }

    /**
     * 分页查询单个对象列表
     * @param qpage
     * @return
     */
    @GetMapping("/find")
    public ResultView find(QPage qpage,@NotBlank(message = "代理商名称不能为空") @RequestParam String agentName,
                           @NotBlank(message = "代理商手机号码不能为空")
                           @Length(min = 11,max = 11,message = "手机号码必须为11位数字")
                           @RequestParam String agentPhone) {
        IPage iPage = new Page(qpage.getPageNo(), qpage.getPageSize());
        IPage page = wxAgentService.page(iPage, null);
        return ResultView.ok(page);
    }

    /**
     * 分页查询多个对象列表
     * @param qPage
     * @return
     */
    @GetMapping("/wx-roles/offset={pageNo}&limit={pageSize}")
    public ResultView findAgentAndRole(QPage qPage) {
        IPage page = wxAgentService.findAgentAndRole(qPage.getPageNo(), qPage.getPageSize());
        return ResultView.ok(page);
    }

    /**
     * 保存对象
     * @param wxAgent
     * @return
     */
    @PostMapping
    public ResultView save(@Valid WxAgent wxAgent){
        return ResultView.ok();
    }

    /**
     * 根据单个值或多个值修改
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultView putById(@PathVariable String id){

        return ResultView.ok();
    }

    /**
     * 根据对象修改
     * @param wxAgent
     * @return
     */
    @PutMapping
    public ResultView putEntity(@Validated WxAgent wxAgent){

        return ResultView.ok();
    }

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultView delById(@PathVariable String id){

        return ResultView.ok();
    }
}
