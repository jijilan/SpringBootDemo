package com.springboot.dlc.service;

import com.springboot.dlc.entity.SysManagerRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员角色表 服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface ISysManagerRoleService extends IService<SysManagerRole> {

    int setRoleByManager(String managerId, String[] roleIds);
}
