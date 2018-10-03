package com.springboot.dlc.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujiebang
 * @since 2018-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysManager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员编号
     */
    @TableId("managerId")
    private String managerId;

    /**
     * 用户名称
     */
    @TableField("userName")
    private String userName;

    /**
     * 用户账号
     */
    @TableField("userAcount")
    private String userAcount;

    /**
     * 管理员权限 1超级管理员 2管理员
     */
    @TableField("managerType")
    private Integer managerType;

    /**
     * 用户密码
     */
    @TableField("passWord")
    private String passWord;

    /**
     * 1:正常 2:禁用
     */
    @TableField("isFlag")
    private Integer isFlag;

    /**
     * 创建时间
     */
    @TableField("cTime")
    private Date cTime;


}
