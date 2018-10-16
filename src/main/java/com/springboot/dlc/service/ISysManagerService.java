package com.springboot.dlc.service;

import com.springboot.dlc.entity.SysManager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface ISysManagerService extends IService<SysManager> {

    /**
     * 管理员登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return
     */
    SysManager login(String userAccount, String userPassword);

}
