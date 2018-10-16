package com.springboot.dlc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.dlc.entity.SysRoleMenu;
import com.springboot.dlc.mapper.SysRoleMenuMapper;
import com.springboot.dlc.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public List<SysRoleMenu> roleMenuListByRoleIdAndMenuId(String roleId, String menuId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(roleId)) {
            queryWrapper.eq("roleId", roleId);
        }
        if (!StringUtils.isEmpty(menuId)) {
            queryWrapper.eq("menuId", menuId);
        }
        return list(queryWrapper);
    }
}
