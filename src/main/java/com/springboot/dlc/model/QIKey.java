package com.springboot.dlc.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/21
 * @Description: Integer 类型的主键
 **/
@Getter
@Setter
public class QIKey {

    @NotNull(message = "主键编号不能为空")
    private Integer key;

}
