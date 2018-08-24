package com.czjsharebed.dlc.exception;

import com.czjsharebed.dlc.result.ResultEnum;
import lombok.Getter;

/**
 * 授权异常类
 */
@Getter
public class AuthException extends Exception {

    private ResultEnum resultEnum;

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum=resultEnum;
    }
}
