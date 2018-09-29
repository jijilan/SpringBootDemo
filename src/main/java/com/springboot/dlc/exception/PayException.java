package com.springboot.dlc.exception;


import com.springboot.dlc.result.ResultEnum;
import lombok.Getter;

@Getter
public class PayException extends RuntimeException {

    private ResultEnum resultEnum;

    public PayException(ResultEnum resultEnum,String message) {
        super(message);
        this.resultEnum=resultEnum;
    }
}
