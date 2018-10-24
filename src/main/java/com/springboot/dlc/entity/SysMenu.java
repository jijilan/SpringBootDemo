package com.springboot.dlc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "主键编号不能为空")
    private String id;

    /**
     * 接口名称
     */
    @NotEmpty(message = "权限名称名称不能为空")
    @TableField("interfaceName")
    private String interfaceName;

    /**
     * 接口地址
     */
    @TableField("interfaceUrl")
    private String interfaceUrl;

    @TableField("pageUrl")
    private String pageUrl;

    /**
     * 父id
     */
    @TableField("fid")
    private String fid;

    /**
     * 接口类型(1:模块 2:菜单 3:按钮)
     */
    @NotNull(message = "接口类型不能为空")
    @TableField("interfaceType")
    private Integer interfaceType;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @TableField("orderBy")
    private Integer orderBy;

    /**
     * 1:有效  2:无效
     */
    @TableField("isFlag")
    private Integer isFlag;

    @TableField(value = "sysMenuList", exist = false)
    private List<SysMenu> sysMenuList;
}
