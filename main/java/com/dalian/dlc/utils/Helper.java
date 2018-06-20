package com.dalian.dlc.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/1/12.
 */
public class Helper {
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 判断字符串是否为空
     * @param str
     * @return  null true
     */
    public static boolean isEmpty(String str){
        if( str == null || str.length() <= 0){
            return true;
        }
        return false;
    }

    public static boolean isGoodJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            JSONObject jsonObject = JSONObject.fromObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static boolean isListEmpty(List<?> list){


        return list == null || list.size() == 0;
    }
    /**
     * 判断map是否为空
     * @param
     * @return
     */
    public static boolean isMapEmpty(Map<?,?> map){


        return map == null || map.size() == 0;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断integer是否大于0
     * @param i
     * @return
     */
    public static boolean isIntegerGtZero(Integer i){

        return i != null && i.intValue() > 0;
    }

    /**
     * 格式化时间    格式：n（分钟/小时/天）前
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateFormat(Date date)
    {
        Date now = new Date();
        long minute = 60;
        long hour = 3600;
        long day = 3600*24;
        long x = (now.getTime()-date.getTime())/1000;
        if(x<60)
        {
            return "1分钟前";
        }
        else if(x>minute && x<hour)
        {
            return (int)x/minute+"分钟前";
        }else if(x>hour && x<day)
        {
            return x/hour+"小时前";
        }/*else if(x>day && x<(day*10))
        {
            return x/day+"天前";
        }*/else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
    }

    /**
     * 获取当前时间yyyy-MM-dd HH:mm:ss
     * @param mode 格式
     * @return
     */
    public static String getYYYYMMDDHHmmss(int mode, Date date){

        String format = "yyyy-MM-dd HH:mm:ss";
        switch (mode) {
            case 1:
                format = "yyyy/MM/dd HH:mm:ss";
                break;
            case 2:
                format = "yyyy.MM.dd HH:mm:ss";
                break;
            case 3:
                format = "yyyy年MM月dd日 HH:mm:ss";
                break;
            case 4:
                format = "yyyyMMddHHmmss";
                break;
            case 5:
                format = "yyyy-MM-dd";
                break;
            case 6:
                format = "yyyy-MM";
                break;
            case 7:
                format = "yyyy-MM-dd HH:mm";
                break;
            default:
                break;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    /**
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天.
     * @throws ParseException 转换异常
     */
    public static int isYeaterday(Date oldTime,Date newTime) throws ParseException {
        if(newTime==null){
            newTime=new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {
            return 0;
        }
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天
            return -1;
        }
        else{ //至少是前天
            return 1;
        }

    }

    public static Timestamp setTimeFormat(String dateStr){
        SimpleDateFormat simpleTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleTime.parse(dateStr);
            return setTimeFormat(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getCurMonthFirstDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(new Date());
        date += "-01 00:00:01";
        return date;
    }


    /**
	 * 转换时间格式(Date to TimeStamp)
	 */
	public static Timestamp setTimeFormat(Date date){
		SimpleDateFormat simpleTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = simpleTime.format(date);
		try {
			Date dateTime = simpleTime.parse(dateStr);
			Timestamp timestamp = new Timestamp(dateTime.getTime());
			return timestamp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 生成随机字符串（session id）,默认64位
     * @param length
     * @return
     */
    public static String generateRandom(Integer length)
    {
        if(length == null)
            length = 16;
        SecureRandom secureRandom = new SecureRandom(Long.toString((new Date()).getTime()).getBytes());
        byte seedBytes[] = secureRandom.generateSeed(length);
        return byteToHex(seedBytes);
    }

    /**
     * 字节与16进制字符串转换
     * @param b
     * @return
     */
    private static String byteToHex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++)
        {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
            {
                hs = hs + "0" + stmp;
            } else
            {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 中文字符串截取
     */
    public static String subStringCN(final String str, final int maxLength) {
        if (str == null) {
            return str;
        }
        String suffix = "...";
        int suffixLen = suffix.length();
        final StringBuffer sbuffer = new StringBuffer();
        final char[] chr = str.trim().toCharArray();
        int len = 0;
        for (int i = 0; i < chr.length; i++) {
            if (chr[i] >= 0xa1) {
                len += 2;
            } else {
                len++;
            }
        }

        if(len<=maxLength){
            return str;
        }

        len = 0;
        for (int i = 0; i < chr.length; i++) {
            if (chr[i] >= 0xa1) {
                len += 2;
                if (len + suffixLen > maxLength) {
                    break;
                }else {
                    sbuffer.append(chr[i]);
                }
            } else {
                len++;
                if (len + suffixLen > maxLength) {
                    break;
                }else {
                    sbuffer.append(chr[i]);
                }
            }
        }
        sbuffer.append(suffix);
        return sbuffer.toString();
    }

    /**
     * Java文件操作 获取文件扩展名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 上传文件
     */
    public static HashMap uploadFile(HttpServletRequest request, MultipartFile file) {
        if (file.isEmpty())
            return null;
        //判断类型

        //判断大小

        //上传
        //绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("");
        //相对路径
        SimpleDateFormat yf = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String uploadPath = "upload/"+yf.format(new Date())+"/"+df.format(new Date())+"/";
        //完整路径
        String path = realPath+"/"+uploadPath;
        //文件名
        String fileName = System.currentTimeMillis()+String.valueOf(random())+"."+Helper.getExtensionName(file.getOriginalFilename());
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        try {
            file.transferTo(targetFile);
            HashMap<String, String> map = new HashMap();
            map.put("uploadPath", uploadPath+fileName);
            map.put("filePath", path+fileName);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 删除文件
     */


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.length()>15)
            return "120.25.79.146";
        return ip;
    }

    /**
     * 获取本机IP
     */
    public static String getLocalIP(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        //System.out.println(ipAddrStr);
        return ipAddrStr;
    }

    /**
     * MD5
     */
    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SHA1算法
     * @param str
     * @return
     */
    public static String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取随机验证码
     * @return
     */
    public static int random(){
        return (int) (Math.random()*8999+1000);
    }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2* R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 格式化距离显示
     * @param d
     * @return
     */
    public static String formatDistance(Double d){

        int k = d.intValue() / 1000;

        int l = d.intValue() % 1000;

        if(k < 1){

            k = d.intValue();

            if(k<300)
                return "<300m";

            return k + "m";
        }else{

            l = l / 100;

            return k + "." + l +"km";
        }

    }

    /**
     * 获得字母数字随机数
     */
    public static String getCharAndNumr(int length)
    {
        String val = "";

        Random random = new Random();
        for(int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }


    /**
     * 图片地址格式化
     */
    /*public static String picFormat(String path) {
        if (StringUtils.isBlank(path))
            return "";
        if (path.substring(0, 4).equals("http"))//如果有 http 就直接返回，如果 没有就拼接好回去
            return path;
        else{
        	return uriPrefix + path;
        }
    }*/

    /**
     *
     * @param pics 如果 pics 为 [{"picUrl":"web/20170513/234234234.jpg"},{"picUrl":"web/20170513/234234267.jpg"}]
     * @return
     */
    public static String getPicsByFilterDomain(String pics) {

        if (!Helper.isEmpty(pics)) {
            if (!Helper.isEmpty(pics)) {
                //过滤空格、换行符、转义符
                pics = pics.replace(" ", "");
                pics = pics.replace("\\n", "");
                pics = pics.replace("\\", "");
            }
            JSONArray jsonArray = new JSONArray();
            String picStr = "";
            try {
                jsonArray = JSONArray.fromObject(pics);
                JSONArray picArray = new JSONArray();
                for (int i=0; i<jsonArray.size(); i++) {
                    String picUrl = jsonArray.getJSONObject(i).getString("picUrl");
                    JSONObject jsonObject = new JSONObject();
                    if (picUrl.substring(0, 4).equals("http")) {
                        if(picUrl.contains("/web"))
                            picUrl = picUrl.substring(picUrl.indexOf("/web") + 1, picUrl.length());
                        if(picUrl.contains("/upload"))
                            picUrl = picUrl.substring(picUrl.indexOf("/upload") + 1, picUrl.length());
                    }
                    jsonObject.put("picUrl",picUrl);
                    picArray.add(jsonObject);
                }

                picStr = picArray.toString();
            } catch (Exception e) {//说明不是 json格式的 字符串 没法转化成json

                if (pics.substring(0, 4).equals("http")) {
                    if(pics.contains("/web"))
                        pics = pics.substring(pics.indexOf("/web") + 1, pics.length());
                    if(pics.contains("/upload"))
                        pics = pics.substring(pics.indexOf("/upload") + 1, pics.length());
                }

                picStr = pics;
            }

            return picStr;
        }
        return null;
    }




    
    private static String rebuilderPicRule(String rules) {
	if(StringUtils.isEmpty(rules))
	    return null;
	Pattern pat = Pattern.compile("(\\d+)h_(\\d+)w");
	Matcher mat =pat.matcher(rules);
	StringBuilder sbu = new StringBuilder(10);
	if(mat.find()){
	    sbu.append("h_").append(mat.group(1)).append(",w_").append(mat.group(2));
	}else
	    return null;
	return sbu.toString();
    }
    
    public enum PicRule1st{
	//将图片按照要求生成缩略图，或者进行特定的缩放。
	RESIZE("image/resize")
	;
	
	private String context; 
	PicRule1st(String context){
	    this.context = context;
	}
	
	public String val(){
	    return this.context;
	}
	
    }
    public enum PicRule2nd{
	//将图强制缩略成宽度为100，高度为100
	M_FIXED("m_fixed"),
	//将图自动裁剪成宽度为100，高度为100的效果图
	M_FILL("m_fill")
	;
	
	private String context; 
	PicRule2nd(String context){
	    this.context = context;
	}
	public String val(){
	    return this.context;
	}
    }
    
    /**
    * <p>getLocalServerNameAll<br>
    * Description:获取http请求"前缀”
    * 
    * @return http请求"前缀”
    *
    */
    public static String getLocalServerNameAll(final HttpServletRequest req){
	//获取当次请求的真实访问域名。此请求头信息应配置到应用服务器或者代理服务器中
	//Note：即时多次重定向或转发，该信息不应该被重写或者擦除。并且头名字应保持是"host"（忽略大小写）
	//如果从head中获取不到，就从当前应用所在操作系统的环境变量中获取。
	String reqHost = req.getServerName();
	if(StringUtils.isEmpty(reqHost))
	    reqHost = req.getHeader("host");
	if(StringUtils.isEmpty(reqHost))
	    reqHost = getDoMain();
	return req.getScheme() + "://" + reqHost + req.getContextPath();
    }
    
    public static String getLocalServerNameMini(final HttpServletRequest req){
        //获取当次请求的真实访问域名。此请求头信息应配置到应用服务器或者代理服务器中
        //Note：即时多次重定向或转发，该信息不应该被重写或者擦除。并且头名字应保持是"host"（忽略大小写）
        //如果从head中获取不到，就从当前应用所在操作系统的环境变量中获取。
        String reqHost = req.getServerName();
        if(StringUtils.isEmpty(reqHost))
            reqHost = req.getHeader("host");
        if(StringUtils.isEmpty(reqHost))
            reqHost = getDoMain();
        return req.getScheme() + "://" + reqHost;
    }
    
    /**
     * <p>getLocalServerNameMini4CB<br>
     * Description:获取微信端直接访问域名
     * 
     * <p>
     * note: 此方法只应被65环境使用，其余环境皆是动态获取。。。。。。
     *
     * @author Slil
     * @version 1.0.0
     * @since 1.0.0
     * 
     * @param req
     * @return 微信端直接访问域名
     *
     */
    public static String getLocalServerNameMini4CB(final HttpServletRequest req){
	return getLocalServerNameMini(req);
	//此方法只应被65环境使用，其余环境皆是动态获取。。。。。。
	//hehehehehehehehehehehehehehehehehehehehehehehehehehehehe
    }
    
    /**
     * <p>getLocalServerName<br>
     * Description:获取当前JVM所在操作系统的环境变量值，即获取当前应用的访问域名<br>
     * 
     * @return 返回在JVM当前所在操作系统中，环境变量名称为 {@link #ENV_NAME_DOMAIN_API_SYSTEM} 的值的值
     * @deprecated {@link #ENV_NAME_DOMAIN_API_SYSTEM}
     */
    @Deprecated
    public static String getDoMain(){
	return "DOMAIN_API_SYS";
    }
    
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static File  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
//        System.out.println("info:"+url+" download success");
        return file;

    }



    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 判断文件夹是否存在
     */
    public static void dirExisted(String path){
        File file =new File("C:\\Users\\QPING\\Desktop\\JavaScript");
        //如果文件夹不存在则创建
        if  (!file .exists()  && !file .isDirectory())
            file .mkdir();
    }

    /**
     * 将 BD-09 坐标转换成 GCJ-02 坐标
     * GoogleMap和高德map用的是同一个坐标系GCJ-02
     * */
    public static HashMap<String, Double> bd_decrypt(double bd_lat, double bd_lon) {
        HashMap<String, Double> longlatMap = new HashMap<String, Double>();
        double gg_lat = 0.0;
        double gg_lon = 0.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        gg_lon = z * Math.cos(theta);
        gg_lat = z * Math.sin(theta);
        longlatMap.put("longitude", gg_lon);
        longlatMap.put("latitude", gg_lat);
        return longlatMap;
    }

    /**
     * 将 GCJ-02 坐标转换成 BD-09 坐标
     * GoogleMap和高德map用的是同一个坐标系GCJ-02
     * */
    public static HashMap<String, Double> bd_encrypt(double gg_lat, double gg_lon) {
        HashMap<String, Double> longlatMap = new HashMap<String, Double>();
        double bd_lat = 0.0;
        double bd_lon = 0.0;
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;
        longlatMap.put("longitude", bd_lon);
        longlatMap.put("latitude", bd_lat);
        return longlatMap;
    }

    public static String urlBegin(HttpServletRequest request){
        ;
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        return tempContextUrl;
    }

    /**
     * 获取 两个日期中 所有的日期
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenDates(String startTime, String endTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin = format.parse(startTime);
            Date end = format.parse(endTime);
            List<String> result = new ArrayList<String>();
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(begin);
            while(begin.getTime()<=end.getTime()){
                result.add(format.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
                begin = tempStart.getTime();
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取当前年份
    public static String curYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static String[] getWeekStartAndEnd(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date t = sdf.parse(date);
            Calendar time = Calendar.getInstance();
            time.setTime(t);
            int a = time.get(Calendar.DAY_OF_WEEK);//获得一周的 第一天
            time.add(Calendar.DAY_OF_WEEK, -(a-2));
            String startStr = sdf.format(time.getTime());
            time.add(Calendar.DAY_OF_WEEK, 6);
            String endStr = sdf.format(time.getTime());
            String[] s = {startStr,endStr};
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    //获取季度的 开始和结束
    public static String[] getSeasonStartAndEnd(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date t  = sdf.parse(date);
            Calendar time = Calendar.getInstance();
            time.setTime(t);
            int m = time.get(Calendar.MONTH)+1;
            int year = time.get(Calendar.YEAR);
            String[] se = new String[2];
            if(m > 0 && m <= 3){//1,2,3
                se[0] = year+"-01-01";
                se[1] = year+"-03-31";
            }else if(m > 3 && m <= 6){//4,5,6
                se[0] = year+"-04-01";
                se[1] = year+"-06-30";
            }else if(m > 6 && m <= 9 ){//7,8,9
                se[0] = year+"-07-01";
                se[1] = year+"-09-30";
            }else if(m > 9 && m <= 12){//10,11,12
                se[0] = year+"-10-01";
                se[1] = year+"-12-31";
            }
            return se;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取给定的任意时间 获该月的开始 时间 和 结束时间
    public static String[] getMonthStartAndEnd(String date){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //List<String> list = new ArrayList<String>();
        try {
            Date t = format.parse(date);
            Calendar time = Calendar.getInstance();
            time.setTime(t);
            //获取当前月的第一天
            time.add(Calendar.MONTH, 0);
            time.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            String firstDay = format.format(time.getTime());
            //获取下个月的最后一天
            time.add(Calendar.MONTH, 1);
            time.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月的最后一天
            String lastDay = format.format(time.getTime());

            //list.add(firstDay);//将第一天存放在第一个位置
            //list.add(lastDay);
            String[] s = {firstDay,lastDay};
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 处理乱码
     */
    public static String dealEncode(String oldCode){
        String newCode  = null;
        if(oldCode!=null){
            try{
                Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m=p.matcher(oldCode);
                if(m.find()){
                    newCode = oldCode;
                }else{
                    newCode = new String(oldCode.getBytes("iso8859_1"),"utf-8");//处理乱码
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                newCode = "";
            }
        }
        return newCode;
    }


    public static String formatMoney(double s, int len)
    {
        NumberFormat formater = null;
        double num = s;
        if (len == 0) {
            formater = new DecimalFormat("######");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("######.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        String result = formater.format(num);
        if(result.indexOf(".") == -1)
        {
            result =  result + ".00";
        }
        else
        {
            result =result;
        }
        return result;
    }
    public static int pageNum(int page){
        return (page-1)*10;
    }

    public static int pageSize(double count){
        return (int)Math.ceil(2.1);
    }


    /*
 * 将时间戳转换为时间
 */
    public static String stampToDate(String s,int num){
        String res;
        SimpleDateFormat simpleDateFormat;
        if(num == 1){
            simpleDateFormat = new SimpleDateFormat("MM月dd");
        }else if(num == -1){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }else{
            simpleDateFormat = new SimpleDateFormat("dd");
        }
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 根据传递的base64，fileType生成不同类型的图片，并且返回jsonArray字符串类型的数组；
     */
//    public static String returnFileUploadUrl(String base64,String fileType){
//        JSONArray jsonArray=new JSONArray();
//        String [] base64list=base64.split(",");
//        boolean result=false;
//        for(int i=0;i<base64list.length;i++) {
//            String fileName=new ValidateCode().getFileUrl(fileType,GXEJHttpUtil.getUUid());
//            if(base64list[i]!=null ||!base64list[i].equals("")) {
//                JSONObject jsonObject=new JSONObject();
//                if(ImageBase64.generateImage(base64list[i], fileName)) {
//                    jsonObject.put("filePath",fileName.substring(fileName.indexOf("/upload")));
//                    jsonArray.add(jsonObject);
//                }
//            }
//        }
//        return jsonArray.toString();
//    }
}
