package com.springboot.dlc.controller.back;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.entity.SysRole;
import com.springboot.dlc.model.QSKey;
import com.springboot.dlc.model.QPage;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysMenuService;
import com.springboot.dlc.service.ISysRoleService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    public ResultView getAuthorityByRole(@Valid QSKey qKey) {
        return iSysRoleService.getAuthorityByRole(qKey.getKey());
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
     * 修改角色信息
     *
     * @param roleName 角色名称
     * @param roleNote 备注
     */
    @PostMapping("/updateRole")
    public ResultView updateRole(@Valid QSKey qKey,
                                 @NotEmpty(message = "角色名称不能为空") String roleName,
                                 @NotEmpty(message = "角色备注信息不能为空") String roleNote) {
        SysRole role = new SysRole()
                .setId(qKey.getKey())
                .setRoleName(roleName)
                .setRoleNote(roleNote);
        return iSysRoleService.updateById(role) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * 获取角色信息
     */
    @GetMapping("/getRoleInfo")
    public ResultView getRoleInfo(@Valid QSKey qKey) {
        return ResultView.ok(iSysRoleService.getById(qKey.getKey()));
    }

    /**
     * 删除角色
     *
     * @param qKey 角色id
     */
    @DeleteMapping("/delRole")
    public ResultView delRole(@Valid QSKey qKey) {
        return iSysRoleService.delRole(qKey.getKey());
    }

    /**
     * 设置角色权限
     *
     * @param qKey  角色id
     * @param menus 菜单id
     * @Description 设置角色权限
     */
    @PutMapping("/setAuthorityByRole")
    public ResultView setAuthorityByRole(@Valid QSKey qKey, @NotNull(message = "菜单编号不能为空") String... menus) {
        return iSysMenuService.setAuthorityByRole(qKey.getKey(), menus);
    }
}
