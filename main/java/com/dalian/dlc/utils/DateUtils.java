package com.dalian.dlc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");


    /**
     * str转Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String str) throws ParseException {
        return format.parse(str);
    }

    /**
     * 当前日期加上天数后的日期
     * @param days 为增加的天数
     * @return
     */
    public static Date addDays(Date oldDate,int days){
          if(oldDate==null){return null;}
          try{
              Calendar ca = Calendar.getInstance();
              ca.setTime(oldDate);
              ca.add(Calendar.DATE, days);// num为增加的天数，可以改变的
              oldDate = ca.getTime();
          }catch(Exception e){
              e.getMessage();
              return null;
          }
          return oldDate;
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
     * 当前日期加上月数后的日期
     * @param month 为增加的月数
     * @return
     */
    public static Date addMonth(Date date,int month){

        System.out.println("当前时间是：" + format.format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month); // 设置为上一个月
        date = calendar.getTime();
        System.out.println(month+"个月的时间： " + format.format(date));
        return date;
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
    public static Map<String,Long> formatDate_Map(String shelfDate,String endTime)  {
        try {
            Map<String,Long> map=new HashMap<String,Long>();
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

    public static String DateAddOneMin(String date){

        try {
            Date date1 = format.parse(date);
            Date afterDate = new Date(date1 .getTime() - 60000);
            return format.format(afterDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateAddS(String date,long s){

        try {
            Date date1 = format.parse(date);
            Date afterDate = new Date(date1 .getTime() + s*1000);
            return format.format(afterDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 计算2个时间之间相隔的天
     * @param shelfDate
     * @param endTime
     * @return
     */
    public static long checkDayByTime(String shelfDate,String endTime)  {
        try {
            Date date1 = format.parse(shelfDate);
            Date date2 = format.parse(endTime);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            return day;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
    //将秒数转换成天，小时，分钟，秒
    public static String formatDateTime(long mss) {
        String DateTimes = null;
        long days = mss / ( 60 * 60 * 24);
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "小时" + minutes + "分钟"
                    + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分钟"
                    + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }

        return DateTimes;
    }
    public static long checkDayByDate(String startDate,String endDate){
        try {
            Date a1 = format.parse(startDate);
            Date b1 = format.parse(endDate);
            //获取相减后天数
            long day = (a1.getTime()-b1.getTime())/(24*60*60*1000);
            return day;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return -1;
    }
    public static long checkHoursByDate(String startDate,String endDate){
        try {
            Date date1 = format.parse(startDate);
            Date date2 = format.parse(endDate);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            return hour;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return -1;
    }
    public static long checkMinutesByDate(String startDate,String endDate){
        try {
            Date date1 = format.parse(startDate);
            Date date2 = format.parse(endDate);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            return min;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return -1;
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
                weeksRight = Arrays.copyOfRange(weeks, i+1, weeks.length);//获取当前星期后面的星期
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
                String num_= String.valueOf(Integer.valueOf(week)+(i+1));
                if (num_.equals(weeks[i])){
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
