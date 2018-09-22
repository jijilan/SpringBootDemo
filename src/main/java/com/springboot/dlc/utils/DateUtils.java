package com.springboot.dlc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("ALL")
public class DateUtils {

    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMAT_CHINESE = "yyyy-MM-dd";

    /**
     * 获取当前日期
     *
     *
     * @return
     *
     */
    public static String getCurrentDate() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        datestr = df.format(new Date());
        return datestr;
    }

    /**
     * 获取当前日期时间
     *
     *
     * @return
     *
     */
    public static String getCurrentDateTime() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        datestr = df.format(new Date());
        return datestr;
    }

    /**
     * 获取当前日期时间
     *
     *
     * @return
     *
     */
    public static String getCurrentDateTime(String dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        datestr = df.format(new Date());
        return datestr;
    }

    /**
     * 将date类型的时间转换成String
     * @param date
     * @return
     */
    public static String dateToDateTime(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        datestr = df.format(date);
        return datestr;
    }
    /**
     * 将字符串日期转换为日期格式
     *
     *
     * @param datestr
     * @return
     *
     */
    public static Date stringToDate(String datestr) {

        if(datestr == null ||"".equals(datestr)){
            return null;
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            date=DateUtils.stringToDate(datestr,"yyyyMMdd");
        }
        return date;
    }

    /**
     * 将字符串日期转换为日期格式
     * 自定義格式
     *
     * @param datestr
     * @return
     *
     */
    public static Date stringToDate(String datestr, String dateformat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }




    /**
     * 将日期格式日期转换为字符串格式
     *
     *
     * @param date
     * @return
     *
     */
    public static String dateToString(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 将日期格式日期转换为字符串格式 自定義格式
     *
     * @param date
     * @param dateformat
     * @return
     */
    public static String dateToString(Date date, String dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 获取日期的DAY值
     *
     *
     * @param date
     *      输入日期
     * @return
     *
     */
    public static int getDayOfDate(Date date) {
        int d = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        d = cd.get(Calendar.DAY_OF_MONTH);
        return d;
    }

    /**
     * 获取日期的MONTH值
     *
     *
     * @param date
     *      输入日期
     * @return
     *
     */
    public static int getMonthOfDate(Date date) {
        int m = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        m = cd.get(Calendar.MONTH) + 1;
        return m;
    }

    /**
     * 获取日期的YEAR值
     *
     *
     * @param date
     *      输入日期
     * @return
     *
     */
    public static int getYearOfDate(Date date) {
        int y = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        y = cd.get(Calendar.YEAR);
        return y;
    }

    /**
     * 获取星期几
     *
     *
     * @param date
     *      输入日期
     * @return
     *
     */
    public static int getWeekOfDate(Date date) {
        int wd = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        wd = cd.get(Calendar.DAY_OF_WEEK) - 1;
        return wd;
    }

    /**
     * 获取输入日期的当月第一天
     *
     *
     * @param date
     *      输入日期
     * @return
     *
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        return cd.getTime();
    }

    /**
     * 获得输入日期的当月最后一天
     *
     * @param date
     */
    public static Date getLastDayOfMonth(Date date) {

        return DateUtils.addDay(DateUtils.getFirstDayOfMonth(DateUtils.addMonth(date, 1)), -1);
    }

    /**
     * 判断是否是闰年
     *
     *
     * @param date
     *      输入日期
     * @return 是true 否false
     *
     */
    public static boolean isLeapYEAR(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int year = cd.get(Calendar.YEAR);

        if (year % 4 == 0 && year % 100 != 0 | year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据整型数表示的年月日，生成日期类型格式
     *
     * @param year
     *      年
     * @param month
     *      月
     * @param day
     *      日
     * @return
     *
     */
    public static Date getDateByYMD(int year, int month, int day) {
        Calendar cd = Calendar.getInstance();
        cd.set(year, month-1, day);
        return cd.getTime();
    }

    /**
     * 获取年周期对应日
     *
     * @param date
     *      输入日期
     * @param iyear
     *      年数  負數表示之前
     * @return
     *
     */
    public static Date getYearCycleOfDate(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        cd.add(Calendar.YEAR, iyear);

        return cd.getTime();
    }

    /**
     * 获取月周期对应日
     *
     * @param date
     *      输入日期
     * @param i
     * @return
     *
     */
    public static Date getMonthCycleOfDate(Date date, int i) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        cd.add(Calendar.MONTH, i);

        return cd.getTime();
    }

    /**
     * 计算 fromDate 到 toDate 相差多少年
     *
     * @param fromDate
     * @param toDate
     * @return 年数
     *
     */
    public static int getYearByMinusDate(Date fromDate, Date toDate) {
        Calendar df=Calendar.getInstance();
        df.setTime(fromDate);

        Calendar dt=Calendar.getInstance();
        dt.setTime(toDate);

        return dt.get(Calendar.YEAR)-df.get(Calendar.YEAR);
    }

    /**
     * 计算 fromDate 到 toDate 相差多少个月
     *
     * @param fromDate
     * @param toDate
     * @return 月数
     *
     */
    public static int getMonthByMinusDate(Date fromDate, Date toDate) {
        Calendar df=Calendar.getInstance();
        df.setTime(fromDate);

        Calendar dt=Calendar.getInstance();
        dt.setTime(toDate);

        return dt.get(Calendar.YEAR)*12+dt.get(Calendar.MONTH)-
                (df.get(Calendar.YEAR)*12+df.get(Calendar.MONTH));
    }

    /**
     * 计算 fromDate 到 toDate 相差多少天
     *
     * @param fromDate
     * @param toDate
     * @return 天数
     *
     */
    public static long getDayByMinusDate(Object fromDate, Object toDate) {

        Date f=DateUtils.chgObject(fromDate);

        Date t=DateUtils.chgObject(toDate);

        long fd=f.getTime();
        long td=t.getTime();

        return (td-fd)/(24L * 60L * 60L * 1000L);
    }

    /**
     * 计算年龄
     *
     * @param birthday
     *      生日日期
     * @param calcDate
     *      要计算的日期点
     * @return
     *
     */
    public static int calcAge(Date birthday, Date calcDate) {

        int cYear=DateUtils.getYearOfDate(calcDate);
        int cMonth=DateUtils.getMonthOfDate(calcDate);
        int cDay=DateUtils.getDayOfDate(calcDate);
        int bYear=DateUtils.getYearOfDate(birthday);
        int bMonth=DateUtils.getMonthOfDate(birthday);
        int bDay=DateUtils.getDayOfDate(birthday);

        if(cMonth>bMonth||(cMonth==bMonth&&cDay>bDay)){
            return cYear-bYear;
        }else{
            return cYear-1-bYear;
        }
    }

    /**
     * 从身份证中获取出生日期
     *
     * @param idno
     *      身份证号码
     * @return
     *
     */
    public static String getBirthDayFromIDCard(String idno) {
        Calendar cd = Calendar.getInstance();
        if (idno.length() == 15) {
            cd.set(Calendar.YEAR, Integer.valueOf("19" + idno.substring(6, 8))
                    .intValue());
            cd.set(Calendar.MONTH, Integer.valueOf(idno.substring(8, 10))
                    .intValue() - 1);
            cd.set(Calendar.DAY_OF_MONTH,
                    Integer.valueOf(idno.substring(10, 12)).intValue());
        } else if (idno.length() == 18) {
            cd.set(Calendar.YEAR, Integer.valueOf(idno.substring(6, 10))
                    .intValue());
            cd.set(Calendar.MONTH, Integer.valueOf(idno.substring(10, 12))
                    .intValue() - 1);
            cd.set(Calendar.DAY_OF_MONTH,
                    Integer.valueOf(idno.substring(12, 14)).intValue());
        }
        return DateUtils.dateToString(cd.getTime());
    }

    /**
     * 在输入日期上增加（+）或减去（-）天数
     *
     * @param date
     *      输入日期
     * @param iday
     *      要增加或减少的天数
     */
    public static Date addDay(Date date, int iday) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.DAY_OF_MONTH, iday);

        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）月份
     *
     * @param date
     *      输入日期
     * @param imonth
     *      要增加或减少的月分数
     */
    public static Date addMonth(Date date, int imonth) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.MONTH, imonth);

        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）年份
     *
     * @param date
     *      输入日期
     * @param iyear
     *      要增加或减少的年数
     */
    public static Date addYear(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.YEAR, iyear);

        return cd.getTime();
    }

    /**
     * 將OBJECT類型轉換為Date
     * @param date
     * @return
     */
    public static Date chgObject(Object date){

        if(date!=null&&date instanceof Date){
            return (Date)date;
        }

        if(date!=null&&date instanceof String){
            return DateUtils.stringToDate((String)date);
        }

        return null;

    }

    public static long getAgeByBirthday(String date){

        Date birthday = stringToDate(date, "yyyy-MM-dd");
        long sec = System.currentTimeMillis() - birthday.getTime();

        long age = sec/(1000*60*60*24)/365;

        return age;
    }

    /**
     * 当前日期加上N天后到当前天24：00的毫秒
     * @param day 为增加的天数
     * @return
     */
    public static long addMillisecond(Date date,int day){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, day+1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Long milliSecond = (cal.getTimeInMillis() - date.getTime());
        return milliSecond;
    }



    /**
     * 当前日期加上天数后的日期
     * @param time 为增加的天数
     * @return
     */
    public static String selWeek(long time){
        Date today = new Date(time);
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        int weekday=c.get(Calendar.DAY_OF_WEEK);
        switch (weekday){
            case 1:
                weekday=7;
                break;
            case 2:
                weekday=1;
                break;
            case 3:
                weekday=2;
                break;
            case 4:
                weekday=3;
                break;
            case 5:
                weekday=4;
                break;
            case 6:
                weekday=5;
                break;
            case 7:
                weekday=6;
                break;
        }
        return String.valueOf(weekday);
    }

    /**
     * 两个日期相减,得到秒数
     */
    public static int calcDays(Date maxDate,Date minDate){
        if(maxDate==null || minDate==null){return 0;}
        long max = maxDate.getTime();
        long min = minDate.getTime();
        int c = (int)((max -min) / 1000);
        return c;
    }

    /**
     * 判断两个日期间隔秒数
     * @return
     */
    public static long checkDate(String beginDate,String endDate){
        SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date bt1=format.parse(beginDate);
            Date bt2=format.parse(endDate);
            int num=DateUtils.calcDays(bt2,bt1);
            return num;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断两个日期间隔小时
     * @return
     */
    public static int checkHourDate(String beginDate,String endDate){
        SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date bt1=format.parse(beginDate);
            Date bt2=format.parse(endDate);
            int num=DateUtils.calcDays(bt2,bt1);
            num=num/60/60;
            return num;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将时间戳转换为Date
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
        String res;
        long lt = new Long(s);
        Date date = new Date(lt);
        res = format.format(date);
        return res;
    }

    /**
     * 计算2个时间之间相隔的小时，分钟，秒
     * @param shelfDate
     * @param endTime
     * @return
     */
    public static String formatDate(String shelfDate,String endTime)  {
        SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date date1 = format.parse(shelfDate);
            Date date2 = format.parse(endTime);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            String differenceTime=day+"天"+hour + "小时" + min +"分" + s + "秒";
            return differenceTime;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算2个时间之间相隔的小时，分钟，秒
     * @param shelfDate
     * @param endTime
     * @return
     */
    public static Map<String,Long> formatDateToMap(String shelfDate,String endTime)  {
        SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Map<String,Long> map=new HashMap<>(4);
            Date date1 = format.parse(shelfDate);
            Date date2 = format.parse(endTime);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            map.put("day",day);
            map.put("hour",hour);
            map.put("min",min);
            map.put("s",s);
            return map;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将秒数转换成天，小时，分钟，秒
     * @param mss 秒
     * @return
     */
    public static String formatDateTime(long mss) {
        String dateTimes = null;
        long days = mss / ( 60 * 60 * 24);
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;
        if(days>0){
            dateTimes= days + "天" + hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(hours>0){
            dateTimes=hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(minutes>0){
            dateTimes=minutes + "分钟"
                    + seconds + "秒";
        }else{
            dateTimes=seconds + "秒";
        }

        return dateTimes;
    }

    /**
     * 判断2个时间段是否有交集
     * @param startdate1
     * @param enddate1
     * @param rightStartDate
     * @param rightEndDate
     * @return
     */
    public static boolean isOverlap(String startdate1, String enddate1,Date rightStartDate, Date rightEndDate) {
        Date leftStartDate = null;
        Date leftEndDate = null;
        SimpleDateFormat format=new SimpleDateFormat(DATE_FORMAT_CHINESE);
        try {
            leftStartDate = format.parse(startdate1);
            leftEndDate = format.parse(enddate1);

        } catch (ParseException e) {
            return true;
        }
        return
                ((leftStartDate.getTime() >= rightStartDate.getTime())
                        && leftStartDate.getTime() < rightEndDate.getTime())
                        ||
                        ((leftStartDate.getTime() > rightStartDate.getTime())
                                && leftStartDate.getTime() <= rightEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() >= leftStartDate.getTime())
                                && rightStartDate.getTime() < leftEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() > leftStartDate.getTime())
                                && rightStartDate.getTime() <= leftEndDate.getTime());

    }

    /**
     * 单位s 计算原点差值
     *
     * @param startTime 预约开始时间
     * @param endTime   预约结束时间
     * @return 0 预约开始时间转换值 1 预约结束时间转换值 单位 秒
     */
    public static long[] calculateTime(long startTime, long endTime) {
        long[] result = new long[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime * 1000);
        // 初始化时间为原点
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long timeOrigin = calendar.getTimeInMillis() / 1000;

        result[0] = startTime - timeOrigin;
        result[1] = (endTime - timeOrigin) % (24 * 3600);

        if (result[0] > result[1]) {
            result[1] += (24 * 3600);
        }

        // 排除时间差
        if (result[1] - result[0] < 5) {
            result[1] = result[0];
        }

        return result;
    }

    public static int sharingCycle(String week ,String [] weeks){
        boolean flag=false;

        String [] weeksRight=new String[0];
        String [] weeksLeft=new String[0];
        int count =0;
        for (int i = 0; i < weeks.length; i++) {
            if (week.equals(weeks[i])){
                flag=true;
                //获取当前星期后面的星期
                weeksRight = Arrays.copyOfRange(weeks, i+1, weeks.length);
                weeksLeft = Arrays.copyOfRange(weeks,0,i);
            }
        }
        if (flag){
            if (weeksRight.length>0){
                if (weeksLeft.length>0){
                    int length=Integer.valueOf(weeksRight.length+weeksLeft.length);
                    weeks=new String[length];
                }else {
                    weeks=new String[weeksRight.length];
                }
            }else if (weeksLeft.length>0){
                weeks=new String[weeksLeft.length];
            }else {
                return 0;
            }
            for (int i = 0; i < weeksRight.length; i++) {
                weeks[i]=weeksRight[i];
            }
            for (int i = 0; i < weeksLeft.length; i++) {
                weeks[weeksRight.length+i]=String.valueOf(Integer.valueOf(weeksLeft[i])+7);
            }
            for (int i = 0; i < weeks.length; i++) {
                String numTwo= String.valueOf(Integer.valueOf(week)+(i+1));
                if (numTwo.equals(weeks[i])){
                    count ++ ;
                }
            }
        }else {
            return -1;
        }
        return count;
    }
    public static void main(String[] args) throws ParseException {

    }
}
