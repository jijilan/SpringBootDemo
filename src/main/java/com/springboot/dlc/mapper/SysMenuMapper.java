package com.springboot.dlc.mapper;

import com.springboot.dlc.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    int delAuthorityById(@Param("roleId") String roleId, @Param("menuId") String menuId);

    int setAuthorityByRole(@Param("roleId") String roleId, @Param("menus") String[] menus);

    List<SysMenu> findModelerByRoleId(@Param("roleId") String roleId);

    List<SysMenu> getMenuListByCondition(@Param("fid") String fid,@Param("roleId") String roleId,@Param("managerId") String managerId);

    List<String> getAuthoritysByManager(@Param("managerId") String managerId);
}
