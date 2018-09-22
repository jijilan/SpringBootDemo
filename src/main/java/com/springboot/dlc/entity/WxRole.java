package com.springboot.dlc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class WxRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理角色编号
     */
    @TableId("roleId")
    private String roleId;

    /**
     * 代理角色名称
     */
    @TableField("roleName")
    private String roleName;

    /**
     * 创建时间
     */
    @TableField("cTime")
    private Date cTime;

    /**
     * 平台直营:0 省代理:1 市代理:2 医院代理:3
     */
    @TableField("roleLevel")
    private Integer roleLevel;


}
