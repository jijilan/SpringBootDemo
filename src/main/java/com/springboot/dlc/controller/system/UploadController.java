package com.springboot.dlc.controller.system;


import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.resources.WebResource;
import com.springboot.dlc.result.ResultEnum;
import com.springboot.dlc.result.ResultView;
import com.springboot.dlc.utils.QRCodeUtil;
import com.springboot.dlc.utils.QiniuUtil;
import com.springboot.dlc.utils.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/8/4
 * @Description: 文件上传
 **/
@Slf4j
@RestController
public class UploadController {

    @Autowired
    private WebResource webResource;

    /**
     * @Description 文件上传
     * @Date 2018/7/11 20:33
     * @Author liangshihao
     */
    @PostMapping("/sys/fileUpload")
    public ResultView fileUpload(@RequestParam("file") MultipartFile[] file, String fileName) throws MyException {
        return ResultView.ok(webResource.getProjectPath() + UploadFileUtil.flowUpload(file, webResource.getStaticResourcePath(), fileName));
    }

    /**
     * 生成设备二维码
     */
    @GetMapping("/downloadQr")
    public void downloadQr(String equipmentNumber, HttpServletResponse response) {
        QRCodeUtil createQrcode = new QRCodeUtil();
        //生成二维码
        try {
            createQrcode.getQrcode(webResource.getEquipmentPath() + equipmentNumber, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三方七牛上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/fileUploadQiniu")
    public ResultView fileUploadQiniu(@RequestParam(value = "file") MultipartFile multipartFile) {
        try {
            byte[] bytes = multipartFile.getBytes();
            return ResultView.ok(QiniuUtil.upLoadImage(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultView.error(ResultEnum.CODE_2);
    }
}
