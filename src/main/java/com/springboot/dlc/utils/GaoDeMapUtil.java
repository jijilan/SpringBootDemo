package com.springboot.dlc.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.dlc.exception.MyException;
import com.springboot.dlc.result.ResultEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/**
 * @Author: liujiebang
 * @Description: 高德
 * @Date: 2018/7/2 17:00
 **/
@Slf4j
public class GaoDeMapUtil {

    /**高德应用的地址*/
    private static final String GAODE_KEY="84ddb8f66da2b8df6ed7817f5a827abc";

    /**地理编码地址*/
    private static final String MAP_CODEURL = "http://restapi.amap.com/v3/geocode/geo";


    /**
     * 输入地址返回识别后的信息
     *
     * @param address
     * @return 返回的类gaodelocation，详见类
     */
    public static String getLocatoin(String address) {
        String location = null;
        RestTemplate restTemplate =new RestTemplate();
        if (address != null) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
                params.add("key", GAODE_KEY);
                params.add("address", address);
                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
                JSONObject jsonObject = restTemplate.postForObject(MAP_CODEURL, httpEntity, JSONObject.class);
                log.info("高德地图返回结果:" + jsonObject);
                if(jsonObject.getString("info").equals("OK")&&Integer.valueOf(jsonObject.getString("count"))>=1){
                    JSONArray jsonArray=jsonObject.getJSONArray("geocodes");
                    location=jsonArray.getJSONObject(0).getString("location");
                }
                return location;
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.CODE_18);
            }
        }
        return location;
    }
}
