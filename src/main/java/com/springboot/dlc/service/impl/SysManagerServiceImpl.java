package com.springboot.dlc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysManagerRole;
import com.springboot.dlc.entity.SysRole;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.mapper.SysManagerMapper;
import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysManagerRoleService;
import com.springboot.dlc.service.ISysManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.dlc.service.ISysMenuService;
import com.springboot.dlc.service.ISysRoleService;
import com.springboot.dlc.utils.DESCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Service
public class SysManagerServiceImpl extends ServiceImpl<SysManagerMapper, SysManager> implements ISysManagerService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ISysMenuService iSysMenuService;
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysManagerRoleService iSysManagerRoleService;

    @Override
    public SysManager login(String userAccount, String userPassword) {
        QueryWrapper<SysManager> qw = new QueryWrapper<>();
        qw.eq("userAcount", userAccount);
        SysManager manager = getOne(qw);
        if (manager == null) {
            throw new MyException(ResultEnum.CODE_14);
        }
        if (!DESCode.encode(userPassword).equals(manager.getPassWord())) {
            throw new MyException(ResultEnum.CODE_15);
        }
        //该管理员的权限集合
        List<String> authorityList = iSysMenuService.getAuthoritysByManager(manager.getManagerId());
        if (authorityList != null && authorityList.size() > 0) {
            redisService.set(ResultStatus.AUTHORITY + manager.getManagerId(), authorityList);
        }
        return manager;
    }

    @Transactional
    @Override
    public ResultView updatePwd(String managerId, String oldPassWord, String passWord) {
        SysManager manager = getById(managerId);
        if (!DESCode.encode(oldPassWord).equals(manager.getPassWord())) {
            return ResultView.error(ResultEnum.CODE_15);
        }
        SysManager updateManager = new SysManager()
                .setManagerId(managerId)
                .setPassWord(DESCode.encode(passWord));
        if (updateById(updateManager)) {
            redisService.del(ResultStatus.PROJECT_NAME + managerId);
            return ResultView.ok();
        }
        return ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public List<SysRole> getSysRoleListByManager(String key) {
        return iSysRoleService.getRoleListByManager(key);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView setRoleByManager(String managerId, String[] roleIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("managerId", managerId);
        if (iSysManagerRoleService.remove(queryWrapper) && iSysManagerRoleService.setRoleByManager(managerId, roleIds) > 0) {
            return ResultView.ok();
        }
        new MyException(ResultEnum.CODE_2);
        return ResultView.error(ResultEnum.CODE_2);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView delManager(String key) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("managerId", key);
        return removeById(key) && iSysManagerRoleService.remove(queryWrapper) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }
}
