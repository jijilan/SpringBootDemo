package com.springboot.dlc.controller.back;


import com.springboot.dlc.entity.SysMenu;
import com.springboot.dlc.model.QKey;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysMenuService;

import java.util.List;

import com.springboot.dlc.utils.IdentityUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@RestController
@RequestMapping("/api/sys-menu/back")
@Validated
public class BackMenuController {

    @Autowired
    private ISysMenuService iSysMenuService;

    /**
     * 获取权限菜单列表
     *
     * @param managerId 管理员id
     */
    @GetMapping("/getMenuList")
    public ResultView getAuthorityMenuList(String managerId, HttpServletRequest request) {
        if (StringUtils.isEmpty(managerId)) {
            managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        }
        return iSysMenuService.getAuthorityMenuList(managerId);
    }

    /**
     * 添加权限菜单
     *
     * @param interfaceName 菜单接口名称
     * @param interfaceUrl  接口地址
     * @param pageUrl
     * @param interfaceType 接口类型(1:模块 2:菜单 3:按钮)
     * @param fid           父id
     * @param orderBy       排序
     */
    @PostMapping("/addMenu")
    public ResultView addAuthorityMenu(@NotBlank(message = "菜单接口名称不能为空") String interfaceName,
                                       @NotNull(message = "菜单接口类型不能为空") Integer interfaceType,
                                       String fid,String interfaceUrl, String pageUrl,
                                       @RequestParam(value = "orderBy", required = false, defaultValue = "99") Integer orderBy) {
        //为菜单时就必须传入fid和url
        if (interfaceType != ResultStatus.INTERFACETYPE_1){
            if (StringUtils.isEmpty(fid)){
                return ResultView.error("父id不能为空");
            }
            if (StringUtils.isEmpty(interfaceUrl)){
                return ResultView.error("接口地址不能为空");
            }
        }
        SysMenu sysMenu = new SysMenu()
                .setId(IdentityUtil.identityId("MEN"))
                .setFid(fid)
                .setInterfaceName(interfaceName)
                .setInterfaceUrl(interfaceUrl)
                .setInterfaceType(interfaceType)
                .setOrderBy(orderBy)
                .setPageUrl(pageUrl);
        return iSysMenuService.save(sysMenu) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }


    /**
     * 获取模块或者菜单
     *
     * @param fid 父id
     */
    @GetMapping("/getMenuByFid")
    public ResultView getMenuByCondition(String fid) {
        List<SysMenu> sysMenuList = iSysMenuService.getMenuByFid(fid);
        return ResultView.ok(sysMenuList);
    }


    /**
     * 获取菜单详情
     *
     * @param key 菜单id
     */
    @GetMapping("/getMenuById")
    public ResultView getMenuInfoById(@Valid QKey key) {
        return ResultView.ok(iSysMenuService.getById(key.getKey()));
    }


    /**
     * 修改权限菜单
     *
     * @param menu
     * @return
     */
    @PutMapping("/putMenu")
    public ResultView putMenu(SysMenu menu) {
        if (menu.getInterfaceType() == ResultStatus.JURISDICTION_MENU || menu.getInterfaceType() == ResultStatus.JURISDICTION_BUTTON) {
            String errMeg = null;
            if (StringUtils.isEmpty(menu.getInterfaceUrl())) {
                errMeg = "接口地址不能为空";
            }
            if (StringUtils.isEmpty(menu.getFid())) {
                errMeg = "父权限不能为空";
            }
            if (!StringUtils.isEmpty(errMeg)) {
                return ResultView.error(errMeg);
            }
        }
        return iSysMenuService.putMenu(menu);
    }

    /**
     * 删除权限菜单
     *
     * @param qKey
     * @return
     */
    @DeleteMapping("/delMenu")
    public ResultView delMenu(@Valid QKey qKey) {
        return iSysMenuService.delMenu((String) qKey.getKey());
    }
}
