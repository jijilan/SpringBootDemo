package com.springboot.dlc.mapper;

import com.springboot.dlc.entity.SysManagerRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 管理员角色表 Mapper 接口
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface SysManagerRoleMapper extends BaseMapper<SysManagerRole> {

    int setRoleByManager(@Param("managerId") String managerId, @Param("roleIds") String[] roleIds);
}
