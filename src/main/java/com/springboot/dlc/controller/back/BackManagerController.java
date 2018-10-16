package com.springboot.dlc.controller.back;


import com.springboot.dlc.controller.base.BaseController;
import com.springboot.dlc.entity.SysManager;
import com.springboot.dlc.result.ResultStatus;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.service.ISysManagerService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/back/logout")
    public ResultView logout(HttpServletRequest request) {
        String managerId = (String) request.getAttribute(ResultStatus.MANAGER_ID);
        redisService.del(managerId);
        return ResultView.ok();
    }
}
