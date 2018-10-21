package com.springboot.dlc.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;



/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/10
 * @Description: String 类型的主键
 **/
@Getter
@Setter
public class QSKey {

    @NotBlank(message = "主键编号不能为空")
    private String key;

}
