package com.springboot.dlc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.WxAgent;
import com.springboot.dlc.mapper.WxAgentMapper;
import com.springboot.dlc.service.IWxAgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liujiebang
 * @since 2018-09-22
 */
@Service
public class WxAgentServiceImpl extends ServiceImpl<WxAgentMapper, WxAgent> implements IWxAgentService {

    @Override
    public IPage findAgentAndRole(Integer pageNo, Integer pageSize) {
        IPage iPage = new Page(pageNo, pageSize);
        List<WxAgent> wxAgentList = baseMapper.findAgentAndRole(iPage);
        iPage.setRecords(wxAgentList);
        return iPage;
    }
}
