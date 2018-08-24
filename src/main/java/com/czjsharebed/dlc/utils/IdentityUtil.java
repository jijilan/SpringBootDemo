package com.czjsharebed.dlc.utils;


import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

public class IdentityUtil {
    /**
     * UUID
     * @return
     */
    public static String uuid(){
        String uuid=String.valueOf(UUID.randomUUID()).replace("-","");
        uuid=uuid.substring(7);
        return uuid;
    }

    /**
     * 生成唯一编号
     * @param title
     * @return
     */
    public static String identityId(String title){
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        //有可能是负数
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        String identityId=title+String.format("%015d", hashCodeV).substring(6)+(System.nanoTime() + "").substring(4, 10);
        return identityId;
    }

    /**
     * 生成手机验证码
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
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
