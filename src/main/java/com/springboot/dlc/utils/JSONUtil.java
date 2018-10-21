package com.springboot.dlc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.*;

/**
 * @auther: liujiebang
 * @Date: Create in 2018/10/21
 * @Description:
 **/
public class JSONUtil {

    /**
     * json字符串转map集合
     *
     * @param jsonStr
     * @return
     */
    public static HashMap<String, String> jsonToMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new HashMap<String, String>().getClass());
    }

    /**
     * map转json字符串
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, String> map) {
        String jsonStr = JSON.toJSONString(map);
        return jsonStr;
    }

    /**
     * json字符串转换成对象
     *
     * @param jsonString
     * @param beanType
     * @return
     */
    public static <T> T jsonToPojo(String jsonString, Class<T> beanType) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * json字符串转换成List集合
     * (需要实体类)
     *
     * @param jsonString
     * @return
     */
    public static <T> List<T> jsonToList(String jsonString, Class beanType) {
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonString, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转换成ArrayList集合
     * (需要实体类)
     *
     * @param jsonString
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String jsonString, Class beanType) {
        ArrayList<T> list = null;
        try {
            list = (ArrayList<T>) JSON.parseArray(jsonString, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List集合转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String listToJson(Object obj) {
        return JSONArray.toJSONString(obj, true);
    }

    /**
     * json转JSONArray
     *
     * @param jsonStr
     * @return
     */
    public static JSONArray jsonToJSONArray(String jsonStr) {
        return JSON.parseArray(jsonStr);
    }

}
