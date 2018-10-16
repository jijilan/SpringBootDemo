package com.springboot.dlc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色名称
     */
    @TableField("roleName")
    private String roleName;

    /**
     * 备注
     */
    @TableField("roleNote")
    private String roleNote;

    private Date ctime;

    /**
     * 1:有效  2:无效
     */
    @TableField("isFlag")
    private Integer isFlag;

    @TableField(value = "sysMenuList", exist = false)
    private List<SysMenu> sysMenuList;
}
