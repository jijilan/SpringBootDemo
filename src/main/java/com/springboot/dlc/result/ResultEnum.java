package com.springboot.dlc.result;

import lombok.Getter;

/**
 * @Author: liujiebang
 * @Description: 自定义参数枚举
 * @Date: 2018/7/2 16:54
 **/
@Getter
public enum ResultEnum {


    CODE_1(1, "操作成功！"),
    CODE_2(2, "操作失败！"),
    CODE_3(3, "用户未登陆！"),
    CODE_4(4, "账号或者密码错误！"),
    CODE_5(5, "图像数据为空！"),
    CODE_6(6, "图像数据传输异常！"),
    CODE_7(7, "图片上传大小不能超过5M！"),
    CODE_8(8, "图片上传格式错误！"),
    CODE_9(9, "手机号码不能为空！"),
    CODE_10(10, "短信验证码不能为空！"),
    CODE_11(11, "短信验证码错误！"),
    CODE_12(12, "登陆类型不能为空！"),
    CODE_13(13, "openId不能为空！"),
    CODE_14(14, "账号不能为空！"),
    CODE_15(15, "密码不能为空！"),
    CODE_16(16, "发送的验证码的类型不能为空！"),
    CODE_17(17, "验证码发送失败！"),
    CODE_18(18, "地址信息不存在！"),
    CODE_19(19, "昵称不能为空！"),
    CODE_20(20, "微信授权失败！"),


    CODE_401(401, "账号或者密码错误！"),
    CODE_403(403, "没有获取授权！"),
    CODE_404(404, "找不到该路径！"),
    CODE_405(405, "请求方式错误！"),
    CODE_415(415, "请求参数格式错误！"),
    CODE_500(500, "服务器出错了，请联系后台开发人员！"),
    CODE_1001(1001, "获取短信验证码失败！"),
    CODE_1002(1002, "服务器数据异常！"),
    CODE_1003(1003, "Payment failed"),
    CODE_1004(1004,"Refund failed"),
    CODE_9999(9999, "服务器无法处理请求！");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
