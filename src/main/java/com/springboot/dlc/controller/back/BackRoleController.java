package com.springboot.dlc.controller.back;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.entity.SysRole;
import com.springboot.dlc.model.QKey;
import com.springboot.dlc.model.QPage;
import com.springboot.dlc.redis.RedisService;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysMenuService;
import com.springboot.dlc.service.ISysRoleService;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@RestController
@RequestMapping("/api/sys-role/back")
@Validated
public class BackRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private ISysMenuService iSysMenuService;

    @Autowired
    private RedisService redisService;

    /**
     * 获取角色列表
     *
     * @param qPage
     * @return
     */
    @GetMapping("/getRoleList")
    public ResultView getRoleList(@Valid QPage qPage) {
        IPage<SysRole> iPage = new Page(qPage.getOffset(), qPage.getLimit());
        IPage<SysRole> page = iSysRoleService.page(iPage, null);
        return ResultView.ok(page);
    }


    /**
     * 获取角色的权限菜单列表
     *
     * @param qKey 角色id
     */
    @GetMapping("/getAuthorityByRole")
    public ResultView getAuthorityByRole(@Valid QKey qKey, HttpServletRequest request) {
        //如果是超级管理员-查询所有权限列表
        String managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        SysManager sysManager = (SysManager) redisService.get(ResultStatus.PROJECT_NAME + managerId);
        if (sysManager.getManagerType() == ResultStatus.MANAGER_ADMIN) {
            return iSysRoleService.getAuthorityByRole(null);
        } else {
            return iSysRoleService.getAuthorityByRole((String) qKey.getKey());
        }

    }

    /**
     * 添加角色
     *
     * @param roleName 角色名称
     * @param roleNote 备注
     */
    @PostMapping("/addRole")
    public ResultView addRole(@NotEmpty(message = "角色名称不能为空") String roleName,
                              @NotEmpty(message = "角色备注信息不能为空") String roleNote) {
        return iSysRoleService.addRole(roleName, roleNote);
    }

    /**
     * 删除角色
     *
     * @param qKey 角色id
     */
    @DeleteMapping("/delRole")
    public ResultView delRole(@Valid QKey qKey) {
        return iSysRoleService.delRole((String) qKey.getKey());
    }

    /**
     * 设置角色权限
     *
     * @param qKey  角色id
     * @param menus 菜单id
     * @Description 设置角色权限
     */
    @PutMapping("/setAuthorityByRole")
    public ResultView setAuthorityByRole(@Valid QKey qKey, @NotBlank(message = "菜单编号不能为空") String... menus) {
        return iSysMenuService.setAuthorityByRole((String) qKey.getKey(), menus);
    }
}
