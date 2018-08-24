

package com.czjsharebed.dlc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * 自定义响应结构
 */
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param Class    对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringToJson(String str) {
        JSONObject json = new JSONObject();
        try {
            if (str.indexOf(",") == -1) {
                return null;
            }
            String[] a = str.split(",");
            for (int i = 0; i < a.length; i++) {
                if (a[i].indexOf(":") == -1) {
                    return null;
                }
                json.put(a[i].split(":")[0], a[i].split(":")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectToJson(json);
    }

    public static Map PageInfoToMap(PageInfo pageInfo, String modelType) {
        Map<String,Object> map=new HashMap<>();
        map.put(modelType,pageInfo.getList());
        map.put("pageNum", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("pages", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        return map;
    }

    /**
     * 根据小时数，判断相应的折扣
     * @param json
     * @param hour
     * @return
     */
    public static Double queryPrice(String json, int hour) {
        NavigableMap<Integer, Double> map1 = new TreeMap<>();


        String[] arr = json.replace("[", "")
                .replace("]", "")
                .replaceAll("\"", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .split(",");

        for (String s : arr) {
            String[] split = s.split(":");
            map1.put(Integer.valueOf(split[0]), Double.valueOf(split[1]));
        }
        Map.Entry<Integer, Double> integerFloatEntry = map1.floorEntry(hour);
        System.out.println(hour + " floorEntry -> " + integerFloatEntry);
        if (integerFloatEntry == null) {
            integerFloatEntry = map1.ceilingEntry(hour);
        }
        return integerFloatEntry.getValue();
    }

    /**
     * 计算停车费用
     *
     * @param costJson     计费标准json
     * @param discountJson 折扣json
     * @param time         停车市场 单位s
     * @param bLongParking 停车类型 true 长停 false 临停
     */
    public static double calculateConsume(String costJson, String discountJson, double time, boolean bLongParking) {

        double amount = 0;

        NavigableMap<Integer, Double> costMap = jsonToMap(costJson);
        NavigableMap<Integer, Double> discountMap = jsonToMap(discountJson);

        int hour = (int) Math.ceil(time / 3600);
        // 天数
        int day = hour / 24;
        // 小时数
        hour = hour % 24;

        // 获取每天费用
        Double dMoney = costMap.floorEntry(24).getValue();
        amount += day * dMoney;

        // 获取每小时费用
        Map.Entry<Integer, Double> hMoneyEntry = costMap.floorEntry(hour);
        double hMoney = 0;
        if (hMoneyEntry == null) {
            hMoneyEntry = costMap.ceilingEntry(hour);
        }
        hMoney = hMoneyEntry.getValue();

        if (bLongParking) {
            // 获取折扣
            Map.Entry<Integer, Double> hCostEntry = discountMap.floorEntry(hour);
            if (hCostEntry == null) {
                hCostEntry = discountMap.ceilingEntry(hour);
            }
            hMoney *= hCostEntry.getValue();
        }else {
            if (time<900){
                return 0;
            }
        }

        amount += hour * hMoney < dMoney ? hour * hMoney : dMoney;
        return amount;
    }

    private static NavigableMap<Integer, Double> jsonToMap(String json) {
        NavigableMap<Integer, Double> map = new TreeMap<>();
        String[] arr = json.replace("[", "")
                .replace("]", "")
                .replaceAll("\"", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .split(",");

        for (String s : arr) {
            String[] split = s.split(":");
            map.put(Integer.valueOf(split[0]), Double.valueOf(split[1]));
        }
        return map;
    }

}

