package com.dalian.dlc.utils;

import com.dalian.dlc.pay.wx.tool.MD5Util;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

public class IdentityUtil {

    public static String uuid(){
        String uuid=String.valueOf(UUID.randomUUID()).replace("-","");
        uuid=uuid.substring(7);
        return uuid;
    }

    public static String identityId(String title){
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        String identityId=title+String.format("%015d", hashCodeV).substring(6)+(System.nanoTime() + "").substring(4, 10);
        return identityId;
    }

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }
    
    public static String getLocalhostIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().toString();
            ip = ip.substring(ip.indexOf("/") + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

}
