package com.czjsharebed.dlc.exception;

import com.czjsharebed.dlc.result.ResultEnum;
import lombok.Getter;
/**
 * @Author: liujiebang
 * @Description: 自定义异常类
 * @Date: 2018/7/2 16:48
 **/
@Getter
public class MyException extends RuntimeException {

    private ResultEnum resultEnum;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum=resultEnum;
    }
}
