package com.czjsharebed.dlc.result;

import lombok.Getter;
/**
 * @Author: liujiebang
 * @Description: 自定义参数枚举
 * @Date: 2018/7/2 16:54
 **/
@Getter
public enum ResultEnum {



    CODE_1(1,"操作成功！"),

    CODE_2(2,"操作失败！"),
    CODE_3(3,"用户未登陆！"),
    CODE_4(4,"账号或者密码错误！"),
    CODE_5(5,"图像数据为空！"),
    CODE_6(6,"图像数据传输异常！"),
    CODE_7(7,"图片上传大小不能超过5M！"),
    CODE_8(8,"图片上传格式错误！"),
    CODE_9(9,"手机号码不能为空！"),
    CODE_10(10,"短信验证码不能为空！"),
    CODE_11(11,"短信验证码错误！"),
    CODE_12(12,"登陆类型不能为空！"),
    CODE_13(13,"openId不能为空！"),
    CODE_14(14,"账号不能为空！"),
    CODE_15(15,"密码不能为空！"),
    CODE_16(16,"发送的验证码的类型不能为空！"),
    CODE_17(17,"验证码发送失败！"),
    CODE_18(18,"地址信息不存在！"),
    CODE_19(19,"昵称不能为空！"),
    CODE_20(20,"微信授权失败！"),

    CODE_21(21,"医院编号不能为空！"),
    CODE_22(22,"分类名称不能为空！"),
    CODE_23(23,"分类描述不能为空！"),
    CODE_24(24,"分类编号不能为空！"),
    CODE_25(25,"分类编号不存在！"),

    CODE_200(200,"code不能为空！"),
    CODE_201(201,"encryptedData不能为空！"),
    CODE_202(202,"iv不能为空！"),
    CODE_203(203,"设备编号不能为空！"),
    CODE_204(204,"充电宝编号不能为空！"),
    CODE_205(205,"订单编号不能为空！"),
    CODE_206(206,"该设备不存在！"),
    CODE_207(207,"该设备还没有代理商，无法使用！"),
    CODE_208(208,"充电宝编号错误！"),
    CODE_209(209,"订单编号错误！"),
    CODE_210(210,"数据异常,该设备存在进行中的订单，请联系管理员！"),
    CODE_211(211,"你还有未完成的订单,请先完成订单，如果丢失请联系客服！"),
    CODE_212(212,"请选择正确的支付类型！"),
    CODE_213(213,"优惠券编号错误！"),
    CODE_214(214,"余额不足！"),
    CODE_215(215,"充值金额不能为空！！"),
    CODE_216(216,"充值选项不存在！！"),
    CODE_217(217,"订单流水号错误！！"),
    CODE_218(218,"请输入正确的类型！！"),
    CODE_219(219,"没有押金记录！"),
    CODE_220(220,"没有押金！"),
    CODE_221(221,"设备已损坏！"),
    CODE_222(222,"设备槽号不能为空！"),
    CODE_223(223,"该充电宝还没生成订单！"),
    CODE_224(224,"还未缴纳押金，无法下单！"),
    CODE_225(225,"该订单已结束！"),
    CODE_226(226,"微信支付失败！"),
    CODE_227(227,"提现间隔不能太频繁！"),
    CODE_228(228,"请输入正确的商家分成！"),
    CODE_100(100,"设备序列号不能为空！"),
    CODE_101(101,"状态值不能为空！"),
    CODE_102(102,"该设备未绑定商户！"),
    CODE_103(103,"该设备已绑定商户,无法删除！"),
    CODE_104(104,"充电宝编号不能为空！"),
    CODE_105(105,"该充电宝下有订单未完成,无法删除！"),
    CODE_106(106,"店铺名称不能为空！"),
    CODE_107(107,"商家名称不能为空！"),
    CODE_108(108,"商户手机号不能为空！"),
    CODE_109(109,"营业开始时间不能为空！"),
    CODE_110(110,"营业结束时间不能为空！"),
    CODE_111(111,"所属行业不能为空！"),
    CODE_112(112,"省市区地址不能为空！"),
    CODE_113(113,"店铺地址不能为空！"),
    CODE_114(114,"营业执照不能为空！"),
    CODE_115(115,"身份证正面不能为空！"),
    CODE_116(116,"身份证反面不能为空！"),
    CODE_117(117,"商家编号不能为空！"),
    CODE_118(118,"设备号不存在！"),
    CODE_119(119,"设备已绑定商户！"),
    CODE_120(120,"经度不能为空！"),
    CODE_121(121,"纬度不能为空！"),
    CODE_122(122,"商户不存在！"),
    CODE_123(123,"请输入搜索条件！"),
    CODE_124(124,"意见内容不能为空！"),
    CODE_125(125,"意见编号不能为空！"),
    CODE_126(126,"充值编号不能为空！"),
    CODE_127(127,"充值金额不能为空！"),
    CODE_128(128,"赠送金额不能为空！"),
    CODE_129(129,"广告图不能为空！"),
    CODE_130(130,"参数类型不能为空！"),
    CODE_131(131,"参数值不能为空！"),
    CODE_132(132,"手机号码已被绑定！"),
    CODE_133(133,"消息编号不能为空！"),
    CODE_134(134,"消息类型不能为空！"),
    CODE_135(135,"消息标题不能为空！"),
    CODE_136(136,"消息内容不能为空！"),
    CODE_137(137,"提交金额不能大于余额！"),
    CODE_138(138,"持卡人姓名不能为空！"),
    CODE_139(139,"银行名称不能为空！"),
    CODE_140(140,"银行卡号不能为空！"),
    CODE_141(141,"所属支行不能为空！"),
    CODE_142(142,"提现金额不能为空！"),
    CODE_143(143,"提现编号不能为空！"),
    CODE_144(144,"审核状态不能为空！"),
    CODE_145(145,"驳回理由不能为空！"),
    CODE_146(146,"用户编号不能为空！"),
    CODE_147(147,"设备号重复！"),
    CODE_148(148,"充电宝序列号重复！"),
    CODE_149(149,"空闲状态不能报失！"),
    CODE_150(150,"报障状态不能报失！"),
    CODE_151(151,"报失状态不能报障！"),
    CODE_152(152,"请勿重复申请代理商！"),
    CODE_153(153,"图片路径不能为空！"),
    CODE_154(154,"该用户已禁用"),
    CODE_155(155,"意见类型不能为空"),
    CODE_156(156,"意见类型不对"),
    CODE_157(157,"无此系统参数"),
    CODE_401(401,"账号或者密码错误！"),
    CODE_403(403,"没有获取授权！"),
    CODE_404(404,"找不到该路径！"),
    CODE_405(405,"请求方式错误！"),
    CODE_415(415,"请求参数格式错误！"),
    CODE_500(500,"服务器出错了，请联系后台开发人员！"),
    CODE_1001(1001,"获取短信验证码失败！"),
    CODE_1002(1002,"服务器数据异常！"),
    CODE_9999(9999,"服务器无法处理请求！");

    private Integer code;

    private String msg;
    ResultEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }

}
