package com.springboot.dlc.controller.back;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.dlc.controller.base.BaseController;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.model.QKey;
import com.springboot.dlc.model.QPage;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysManagerRoleService;
import com.springboot.dlc.service.ISysManagerService;
import com.springboot.dlc.service.ISysRoleService;
import com.springboot.dlc.utils.DESCode;
import com.springboot.dlc.utils.IdentityUtil;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@RestController
@RequestMapping("/api/sys-manager")
@Validated
public class BackManagerController extends BaseController {

    @Autowired
    private ISysManagerService iSysManagerService;
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysManagerRoleService iSysManagerRoleService;

    /**
     * 后台登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    @PostMapping(value = "/login")
    public ResultView login(@NotEmpty(message = "用户名不能为空") String userAccount,
                            @NotEmpty(message = "密码不能为空")
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String userPassword) {
        SysManager manager = iSysManagerService.login(userAccount, userPassword);
        String token = jwtToken(ResultStatus.MANAGER_ID, manager.getManagerId(), manager);
        return ResultView.ok(token);
    }

    /**
     * 修改密码
     *
     * @param passWord
     * @param request
     * @return
     */
    @PutMapping(value = "/back/updatePwd")
    public ResultView updatePwd(HttpServletRequest request, @NotEmpty(message = "原密码不能为空") String oldPassWord,
                                @NotEmpty(message = "新密码不能为空") @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String passWord) {
        String managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        return iSysManagerService.updatePwd(managerId, oldPassWord, passWord);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/back/logout")
    public ResultView logout(HttpServletRequest request) {
        String managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        redisService.del(ResultStatus.PROJECT_NAME + managerId);
        return ResultView.ok();
    }

    /**
     * 新增管理员
     *
     * @param account
     * @param password
     * @param username
     * @return
     */
    @PostMapping("/back/addManager")
    public ResultView addManager(@NotEmpty(message = "账号不能为空") String account, @NotEmpty(message = "名称不能为空") String username
            , @NotEmpty(message = "密码不能为空") @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAcount", account);
        if (iSysManagerService.count(queryWrapper) > 0) {
            return ResultView.error("账号已存在");
        }
        SysManager manager = new SysManager()
                .setManagerId(IdentityUtil.identityId("MAN"))
                .setUserName(username)
                .setPassWord(DESCode.encode(password))
                .setUserAcount(account)
                .setManagerType(ResultStatus.MANAGER_GENERAL)
                .setCTime(new Date())
                .setIsFlag(ResultStatus.ISFLAG_Y);
        return iSysManagerService.save(manager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * 获取管理员列表
     *
     * @return
     */
    @GetMapping("/back/getManagetList")
    public ResultView getManagetList(@Valid QPage qPage) {
        IPage<SysManager> iPage = new Page(qPage.getOffset(), qPage.getLimit());
        IPage<SysManager> page = iSysManagerService.page(iPage, null);
        return ResultView.ok(page);
    }

    /**
     * 删除管理员
     *
     * @param qKey
     * @return
     */
    @DeleteMapping("/back/delManager")
    public ResultView delManager(@Valid QKey qKey) {
        return iSysManagerService.delManager((String)qKey.getKey());
    }

    /**
     * 修改管理员名称
     *
     * @param managerId
     * @param userName
     * @return
     */
    @PutMapping(value = "/back/updateManager")
    public ResultView updateManager(@NotEmpty(message = "管理员id不能为空") String managerId, @NotEmpty(message = "用户名称不能为空") String userName) {
        SysManager manager = new SysManager().setManagerId(managerId).setUserName(userName);
        return iSysManagerService.updateById(manager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * 获取管理员的角色列表
     *
     * @param qKey 管理员id
     * @return
     */
    @GetMapping("/back/getRoleListByManager")
    public ResultView getRoleListByManager(@Valid QKey qKey) {
        return ResultView.ok(iSysManagerService.getSysRoleListByManager((String) qKey.getKey()));
    }

    /**
     * 设置管理员的权限角色
     *
     * @param qKey    管理员id
     * @param roleIds
     * @return
     */
    @PutMapping("/back/setRoleByManager")
    public ResultView setRoleByManager(@Valid QKey qKey, @NotEmpty(message = "角色集合不能为空") String... roleIds) {
        return iSysManagerService.setRoleByManager((String) qKey.getKey(), roleIds);
    }


}
