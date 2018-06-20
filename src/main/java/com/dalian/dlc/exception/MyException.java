package com.dalian.dlc.exception;

import com.dalian.dlc.result.ResultEnum;
import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

    private ResultEnum resultEnum;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum=resultEnum;
    }
}
