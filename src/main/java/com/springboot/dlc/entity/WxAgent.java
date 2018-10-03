package com.springboot.dlc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * <p>
 * 
 * </p>
 *
 * @author liujiebang
 * @since 2018-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理人编号
     */
    @NotBlank(message = "代理人编号不能为空")
    @TableId("agentId")
    private String agentId;

    /**
     * 代理人账号
     */
    @TableField("agentAccount")
    private String agentAccount;

    /**
     * 代理人密码
     */
    @NotBlank(message = "代理人密码不能为空")
    @Length(min = 8,max = 16,message = "密码长度必须在8~16位之间")
    @TableField("agentPassWord")
    private String agentPassWord;

    /**
     * 代理人名称
     */
    @TableField("agentName")
    private String agentName;

    /**
     * 代理人手机
     */
    @TableField("agentPhone")
    private String agentPhone;

    /**
     * 省
     */
    @TableField("agentProvince")
    private String agentProvince;

    /**
     * 市
     */
    @TableField("agentCity")
    private String agentCity;

    /**
     * 区
     */
    @TableField("agentArea")
    private String agentArea;

    /**
     * 医院管理员【根据医院编号查询】
     */
    @TableField("hospitalId")
    private String hospitalId;

    /**
     * 1:正常 2：禁用
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 角色编号
     */
    @TableField("roleId")
    private String roleId;

    /**
     * 创建时间
     */
    @TableField("cTime")
    private Date cTime;

    /**
     * 佣金比例【1-99】
     */
    @TableField("commissionRatio")
    private Integer commissionRatio;

    @TableField(value = "wxRole",exist = false)
    private WxRole wxRole;

}
