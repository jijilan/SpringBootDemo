package com.dalian.dlc.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shuige on 2017/11/2.
 */
public class LotteryUtil {
    //uat
    private static String    msgId;
    private static String    msgCreateTime;
    private static String    sender;
    private static String    receiver;
    private static String    autograph;
    private static String    encryptionMode;
    private static String    interfaceCode;
    private static JSONObject paramJSON;
    static{
        setMsgId(getUUid());
        setMsgCreateTime(getdata("yyyy-MM-dd HH:mm:ss"));
    }

    public LotteryUtil(String requestMethod, JSONObject paramJSON){
        this.setInterfaceCode(requestMethod);
        this.setParamJSON(paramJSON);
    }


    public static Map getDataJSON()throws Exception{
        JSONObject obj=new JSONObject();
        obj.put("HEAD",getHeadJSON());
        obj.put("BODY",getBodyJSON());
        Map<String,Object> paramValue=new HashMap<String,Object>();
        paramValue.put("jsonValue",obj.toString());
        return paramValue;
    }

    public static JSONArray getBodyJSON() throws Exception{
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(getParamJSON());
        return jsonArray;
    }

    public static JSONObject getHeadJSON() throws Exception{
        JSONObject obj=new JSONObject();
        obj.put("msgId",getMsgId());
        obj.put("msgCreateTime",getMsgCreateTime());
        obj.put("sender",getSender());
        obj.put("receiver",getReceiver());
        obj.put("autograph",getReceiver());
        obj.put("encryptionMode",getEncryptionMode());
        obj.put("interfaceCode",getInterfaceCode());
        return obj;
    }

    public static String getdata(String format)
    {
        SimpleDateFormat sf=new SimpleDateFormat(format);
        return sf.format(new Date());
    }

    public static Date stringParseDate(String time,String formart){
        Date date=new Date();
        try {
            SimpleDateFormat sf=new SimpleDateFormat(formart);
            date= sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //验证码--手机
    public static String getRandomNum(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(10);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    //随机数
    public static String getRandomString(int length) {
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public static String getOrdersId(String title){
        return title+collectionsNum(LotteryUtil.getdata("yyyyMMdd")+LotteryUtil.getRandomNum(4));
    }

    public static String getOutTradeNo(){
        return "UTN"+collectionsNum(LotteryUtil.getdata("yyyyMMdd")+LotteryUtil.getRandomNum(4));
    }

    //生成主键ID--将字符串先生成出来，然后乱序
    public static String getUUid(){
        String uuid=getdata("yyyyMMddHHssmm")+getRandomString(7);
        return LotteryUtil.collectionsNum(uuid);
    }

    //生成设备编号
    public static String getEquipmenNum(){
        Calendar calendat = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(calendat.getTime()).substring(2, 4);
        return dateStr + "-" + LotteryUtil.getRandomNum(4);
    }

    //生成邀请码
    public static String getInvitationCode(){
        String code=getRandomString(6);
        return collectionsNum(code);
    }

    public static String collectionsNum(String strNum){
        char [] str=strNum.toCharArray();
        List<String> randRum=new ArrayList<String>();
        for(int i=0;i<str.length;i++){
            randRum.add(String.valueOf(str[i]));
        }
        Collections.shuffle(randRum);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<randRum.size();i++){
            sb.append(randRum.get(i));
        }
        return sb.toString().trim();
    }
    public static String getJSONKey(JSONObject jsonObject){
        Iterator iterator = jsonObject.keys();
        StringBuilder sb = new StringBuilder();
        while(iterator.hasNext()){
            String key = (String) iterator.next();
            if(key!=null||!key.equals("")){
                sb.append(key+",");
            }
        }
        return sb.substring(0, sb.toString().length() - 1);
    }

    public static String getMsgId() {
        return msgId;
    }

    public static void setMsgId(String msgId) {
        LotteryUtil.msgId = msgId;
    }

    public static String getMsgCreateTime() {
        return msgCreateTime;
    }

    public static void setMsgCreateTime(String msgCreateTime) {
        LotteryUtil.msgCreateTime = msgCreateTime;
    }

    public static String getSender() {
        return sender;
    }

    public static void setSender(String sender) {
        LotteryUtil.sender = sender;
    }

    public static String getReceiver() {
        return receiver;
    }

    public static void setReceiver(String receiver) {
        LotteryUtil.receiver = receiver;
    }

    public static String getAutograph() {
        return autograph;
    }

    public static void setAutograph(String autograph) {
        LotteryUtil.autograph = autograph;
    }

    public static String getEncryptionMode() {
        return encryptionMode;
    }

    public static void setEncryptionMode(String encryptionMode) {
        LotteryUtil.encryptionMode = encryptionMode;
    }

    public static String getInterfaceCode() {
        return interfaceCode;
    }

    public static void setInterfaceCode(String interfaceCode) {
        LotteryUtil.interfaceCode = interfaceCode;
    }

    public static JSONObject getParamJSON() {
        return paramJSON;
    }

    public static void setParamJSON(JSONObject paramJSON) {
        LotteryUtil.paramJSON = paramJSON;
    }

    //获取两个时间端的秒数间隔
    public static  int calLastedTime(Date endDate) {
        long a = endDate.getTime();
        long b = new Date().getTime();
        int c = 0;
        if(a > b){
             c = (int)((a - b) / 1000);
        }
        return c;
    }

}
