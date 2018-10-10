package com.springboot.dlc.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/10
 * @Description:
 **/
@Getter
@Setter
public class QKey {

    @NotNull(message = "主键编号不能为空")
    private Serializable key;
}
