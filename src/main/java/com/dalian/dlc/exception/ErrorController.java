package com.dalian.dlc.exception;

import com.dalian.dlc.result.ResultView;
import com.dalian.dlc.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public ResultView handleError(HttpServletResponse response) {
        log.info("请求错误:{}",response.getStatus());
        if (response.getStatus()==401){
            return ResultView.error(ResultEnum.CODE_401);
        }else if (response.getStatus()==403){
            return ResultView.error(ResultEnum.CODE_403);
        }else if (response.getStatus()==404){
            return ResultView.error(ResultEnum.CODE_404);
        }else if (response.getStatus()==405){
            return ResultView.error(ResultEnum.CODE_405);
        }else if (response.getStatus()==415){
            return ResultView.error(ResultEnum.CODE_415);
        }
        return ResultView.error(ResultEnum.CODE_9999);
    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
