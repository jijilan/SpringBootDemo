package com.springboot.dlc.mapper;

import com.springboot.dlc.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRoleListByManager(@Param("managerId") String managerId);
}
