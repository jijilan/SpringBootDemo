package com.springboot.dlc.service;

import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> selectRoleByManager(SysManager sysManager);
}
