package com.czjsharebed.dlc.controller;

import com.czjsharebed.dlc.exception.MyException;
import com.czjsharebed.dlc.result.ResultView;
import com.czjsharebed.dlc.utils.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/8/4
 * @Description: 文件上传
 **/
@Slf4j
@RestController
public class UploadController {

    /**
     * @Description 文件上传
     * @Date 2018/7/11 20:33
     * @Author liangshihao
     */
    @PostMapping("/fileUpload")
    public ResultView fileUpload(@RequestParam("file") MultipartFile[] file, String fileName) throws MyException {
        return ResultView.ok(UploadFileUtil.flowUpload(file, fileName));
    }


}
