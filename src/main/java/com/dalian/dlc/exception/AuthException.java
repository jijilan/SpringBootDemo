package com.dalian.dlc.exception;

import com.dalian.dlc.result.ResultEnum;
import lombok.Getter;

/**
 * 授权异常类
 */
@Getter
public class AuthException extends RuntimeException {

    private ResultEnum resultEnum;

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum=resultEnum;
    }
}
