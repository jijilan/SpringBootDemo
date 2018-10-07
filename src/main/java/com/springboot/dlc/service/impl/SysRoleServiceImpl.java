package com.springboot.dlc.service.impl;

import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysRole;
import com.springboot.dlc.mapper.SysRoleMapper;
import com.springboot.dlc.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<SysRole> selectRoleByManager(SysManager sysManager) {

        return null;
    }
}
