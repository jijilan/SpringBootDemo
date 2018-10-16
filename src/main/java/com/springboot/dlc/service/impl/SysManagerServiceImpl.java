package com.springboot.dlc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.mapper.SysManagerMapper;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.dlc.utils.DESCode;
import org.springframework.stereotype.Service;

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
        return manager;
    }
}
