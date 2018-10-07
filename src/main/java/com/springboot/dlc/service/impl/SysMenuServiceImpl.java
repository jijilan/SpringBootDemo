package com.springboot.dlc.service.impl;

import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysMenu;
import com.springboot.dlc.mapper.SysMenuMapper;
import com.springboot.dlc.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<SysMenu> selectMenuByManager(SysManager sysManager) {
        return null;
    }
}
