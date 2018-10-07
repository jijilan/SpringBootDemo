package com.springboot.dlc.service;

import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> selectMenuByManager(SysManager sysManager);
}
