package com.springboot.dlc.service;

import com.springboot.dlc.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    List<SysRoleMenu> roleMenuListByRoleIdAndMenuId(String roleId,String menuId);


}
