package com.springboot.dlc.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/8/29
 * @Description: 七牛文件上传
 **/
public class QiniuUtil {
    //根据七牛云的个人中心来填写
    private static final String accessKey = "lKzA4uhqChNaHD2R-tXyASQ4ojLBAmGcbgliVAYw";
    private static final String secretKey = "v55jr_egGjHE6nmzIael0fBPTqPcjBxuXUdYtU5j";
    private static final String bucket = "liu15019200770@163.com";
    private static String httpURL="http://p5k92qj55.bkt.clouddn.com/";

    /**
     * @param  data
     * @return  外键地址
     */
    public static String upLoadImage(byte[] data){
        //图片的外链地址
        StringBuffer imgUrl  = new StringBuffer(httpURL);

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            //上传文件
            Response response = uploadManager.put(data,key,upToken);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            imgUrl.append(putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return imgUrl.toString();
    }

    public static void main(String[] args) {
        //：一个无序数组，其中一个数字出现的次数大于其他数字之和，求这个数字 （主元素）
        int [] nums={1,2,3,4,5,3,1,4,6,7,24,13,14,21,1,62,14,4,21,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
        Arrays.sort(nums);
        int num =1;
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
            if ((i+1) == nums.length){
                break;
            }
            if (nums[i] == nums[i+1]){
                num ++;
            }else {
                map.put(nums[i],num);
                num =1;
            }
        }
        System.out.println(map);
    }
}
