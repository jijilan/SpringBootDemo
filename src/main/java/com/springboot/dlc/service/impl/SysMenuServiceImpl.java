package com.springboot.dlc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysMenu;
import com.springboot.dlc.entity.SysRoleMenu;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.mapper.SysMenuMapper;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.dlc.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private ISysRoleMenuService iSysRoleMenuService;


    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView setAuthorityByRole(String roleId, String[] menus) {
        return (baseMapper.delAuthorityById(roleId, null) >= 0 && baseMapper.setAuthorityByRole(roleId, menus) >= 0)
                ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public ResultView getAuthorityMenuList(String managerId) {

        return null;
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView delMenu(String key) {
        //如果有角色绑定该权限-不允许删除
        List<SysRoleMenu> sysRoleMenus = iSysRoleMenuService.roleMenuListByRoleIdAndMenuId(null, key);
        if (sysRoleMenus != null && sysRoleMenus.size() > 0) {
            return ResultView.error(ResultEnum.CODE_23);
        }
        //如果当前权限下有子权限-不允许删除
        checkMenuLowerLevel(key);
        return removeById(key) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public List<SysMenu> getMenuByFid(String key) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fid", key);
        queryWrapper.orderByAsc("orderBy");
        return list(queryWrapper);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView putMenu(SysMenu menu) {
        SysMenu sysMenu = getById(menu.getId());
        //需要判断当前权限是否在降低权限操作
        if (sysMenu.getInterfaceType() >= menu.getInterfaceType()) {
            checkMenuLowerLevel(menu.getId());
        }
        return updateById(menu) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public List<SysMenu> findModelerByRoleId(String roleId) {
        return baseMapper.findModelerByRoleId(roleId);
    }

    @Override
    public List<SysMenu> findRecursionById(String key) {
        List<SysMenu> sysMenuList = getMenuByFid(key);
        if (key != null && sysMenuList != null && sysMenuList.size() > 0) {
            for (SysMenu menu : sysMenuList) {
                menu.setSysMenuList(findRecursionById(menu.getId()));
            }
        }
        return sysMenuList;
    }

    /**
     * 检查下级
     *
     * @param key
     * @return
     */
    private void checkMenuLowerLevel(String key) {
        List<SysMenu> list = getMenuByFid(key);
        //当前权限有子权限，不允许修改
        if (list != null && list.size() > 0) {
            throw new MyException(ResultEnum.CODE_22);
        }
    }
}
