package com.springboot.dlc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.dlc.entity.WxAgent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-09-22
 */
public interface IWxAgentService extends IService<WxAgent> {

    IPage findAgentAndRole(Integer pageNo, Integer pageSize);
}
