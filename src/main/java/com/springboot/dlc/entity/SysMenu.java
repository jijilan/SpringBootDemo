package com.springboot.dlc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    private String id;

    /**
     * 接口名称
     */
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
    private String fid;

    /**
     * 接口类型(1:模块 2:菜单 3:按钮)
     */
    @TableField("interfaceType")
    private Integer interfaceType;

    /**
     * 排序
     */
    @TableField("orderBy")
    private Integer orderBy;

    /**
     * 1:有效  2:无效
     */
    @TableField("isFlag")
    private Integer isFlag;


}
