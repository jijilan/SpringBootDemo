package com.czjsharebed.dlc.utils;

import com.czjsharebed.dlc.result.ResultView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class EquipmentUtil {


    //	心跳回调地址
    public static final String EQUIPMENT_RETURN_URL = "http://maodian.dlc-sz.com/heartbeat";
    //	还设备回调地址
    public static final String EQUIPMENT_BACK_URL = "http://maodian.dlc-sz.com/equimentRepay";

    public static final String EQUIPMENT_RQUEST_URL = "http://120.77.72.190/Charger/setCallBack";


     /**
     * 设置硬件回调地址
     *
     * @return
     */
    public static ResultView setEquipmentReturnUrl() {
        RestTemplate restTemplate = new RestTemplate();
        String sign = MD5Util.MD5Encode("water", "utf-8");
        System.out.println(sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("back_charge_url", EQUIPMENT_BACK_URL);
        params.add("url", EQUIPMENT_RETURN_URL);
        params.add("sign", sign);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        String json = restTemplate.postForObject(EQUIPMENT_RQUEST_URL, httpEntity, String.class);
        ResultView resultView = JsonUtils.jsonToPojo(json, ResultView.class);
        System.out.println(resultView.getMsg());
        return resultView;
    }


}
