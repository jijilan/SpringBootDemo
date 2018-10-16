package com.springboot.dlc.service.impl;

import com.springboot.dlc.entity.SysMenu;
import com.springboot.dlc.entity.SysRole;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.mapper.SysRoleMapper;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysMenuService;
import com.springboot.dlc.service.ISysRoleMenuService;
import com.springboot.dlc.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.dlc.utils.IdentityUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMenuService iSysRoleMenuService;

    @Autowired
    private ISysMenuService iSysMenuService;

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView addRole(String roleName, String roleNote) {
        SysRole sysRole = new SysRole().setId(IdentityUtil.identityId("ROL")).setRoleName(roleName).setRoleNote(roleNote);
        return save(sysRole) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView delRole(String key) {
        return removeById(key) && iSysRoleMenuService.removeById(key) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public ResultView getAuthorityByRole(String key) {
        List<SysMenu> sysMenuList = iSysMenuService.findModelerByRoleId(key);
        if (sysMenuList != null && sysMenuList.size() > 0) {
            for (int i = 0; i < sysMenuList.size(); i++) {
                sysMenuList.get(i).setSysMenuList(iSysMenuService.findRecursionById(sysMenuList.get(i).getId()));
            }
        }
        return ResultView.ok(sysMenuList);
    }
}
