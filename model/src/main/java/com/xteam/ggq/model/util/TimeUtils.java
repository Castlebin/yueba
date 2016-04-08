package com.xteam.ggq.model.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Created by yuanguanghui on 2015/10/13.
 * 日期时间处理工具类
 *
 */
@Slf4j
public final class TimeUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT3 = "MM/dd/yyyy HH:mm:ss";
    public static final String FORMAT4 = "MM/dd/yyyy";
    public static final String FORMAT5 = "HH:mm:ss";
    public static final String FORMAT6 = "yyyyMMddHHmmss";
    public static final String FORMAT7 = "yyyy-MM";
    public static final String FORMAT8 = "yyyy年MM月dd日";
    public static final String ZH_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日 hh时mm分ss秒";
    public static final String FORMAT10 = "yyyy年MM月dd日 E hh时mm分ss秒";
    public static final String FORMAT11 = "MM月dd日 HH:mm";

    public static final int DATATYPE_YEAR = 1;
    public static final int DATATYPE_MONTH = 2;
    public static final int DATATYPE_DAY = 3;
    public static final int DATATYPE_HOUR = 4;
    public static final int DATATYPE_MINUTE = 5;
    public static final int DATATYPE_SECOND = 6;

    /**
     * 获取日期格式
     * 支持的格式有：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS（归到yyyy-MM-dd HH:mm:ss中）
     * yyyy-MM-dd
     * MM/dd/yyyy HH:mm:ss
     * MM/dd/yyyy HH:mm:ss.SSS（归到MM/dd/yyyy HH:mm:ss中）
     * MM/dd/yyyy
     * @param date  日期
     * @return      格式
     */
    public static String getFormat(String date) throws Exception
    {
        String reg1 = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(.\\d{1,3}){0,1}";
        String reg2 = "\\d{4}-\\d{1,2}-\\d{1,2}";
        String reg3 = "\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}(.\\d{1,3}){0,1}";
        String reg4 = "\\d{1,2}/\\d{1,2}/\\d{4}";
        if (date.matches(reg1))
        {
            return YYYY_MM_DD_HH_MM_SS;
        }
        else if (date.matches(reg2))
        {
            return YYYY_MM_DD;
        }
        else if (date.matches(reg3))
        {
            return FORMAT3;
        }
        else if (date.matches(reg4))
        {
            return FORMAT4;
        }
        else
        {
            throw new Exception("不支持的日期格式：" + date);
        }
    }

    /**
     * 获取当前日期（固定格式：yyyy-MM-dd）
     * @return  当前日期
     */
    public static String getCurrentDate()
    {
        return getCurrentDateTime(YYYY_MM_DD);
    }

    /**
     * 获取当前时间（固定格式：HH:mm:ss）
     * @return  当前时间
     */
    public static String getCurrentTime()
    {
        return getCurrentDateTime(FORMAT5);
    }

    /**
     * 获取当前日期和时间（固定格式：yyyy-MM-dd HH:mm:ss）
     * @return  当前日期和时间
     */
    public static String getCurrentDateTime()
    {
        return getCurrentDateTime(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前日期和时间（自定义格式）
     * 参考格式：yyyy年MM月dd日HH时（hh时）mm分ss秒ms毫秒E本月第F个星期
     * 对应的值：2009年07月22日15时（03时）05分30秒530毫秒星期三本月第4个星期
     * @param format    日期时间的格式
     * @return          当前日期和时间
     */
    public static String getCurrentDateTime(String format)
    {
        return DateFormatUtils.format(new Date(), format);
    }

    /**
     * 获取昨天的日期（固定格式：yyyy-MM-dd）
     * @return  日期
     */
    public static String getYesterday()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return DateFormatUtils.format(c.getTime(), YYYY_MM_DD);
    }

    /**
     * 获取明天的日期（固定格式：yyyy-MM-dd）
     * @return  日期
     */
    public static String getTomorrow()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return DateFormatUtils.format(c.getTime(), YYYY_MM_DD);
    }

    /**
     * 把String转换成日期
     * @param date      String型日期
     * @return          Date型日期
     */
    public static Date convertStringToDate(String date) throws Exception
    {
        return DateUtils.parseDate(date, getFormat(date));
    }

    /**
     * 把日期类型转换成String
     * @param date      Date型日期
     * @param format    转换成String型日期后的格式
     * @return          String型日期
     */
    public static String convertDateToString(Date date, String format)
    {
        return DateFormatUtils.format(date, format);
    }

    /**
     * 日期时间格式转换
     * @param value         转换前的值
     * @param format        转换后的格式
     * @return              转换后的值
     */
    public static String dateFormat(String value, String format)
    {
        try
        {
            Date date = convertStringToDate(value);
            return convertDateToString(date, format);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * 计算两个日期的间隔（yyyy MM dd HH mm ss）
     * @param type      间隔的单位：1-年，2-月，3-日，4-小时，5-分钟，6-秒，不填默认为日
     * @param sdate1    String型日期
     * @param sdate2    String型日期
     * @return          间隔的数量。如果日期sdate2在日期sdate1之后，则结果为正数；如果日期sdate2在日期sdate1之前，则结果为负数
     */
    public static int dateDiff(int type, String sdate1, String sdate2) throws Exception
    {
        return dateDiff(type, DateUtils.parseDate(sdate1, getFormat(sdate1)), DateUtils.parseDate(sdate2, getFormat(sdate2)));
    }

    /**
     * 计算两个日期的间隔（yyyy MM dd HH mm ss）
     * @param type      间隔的单位：1-年，2-月，3-日，4-小时，5-分钟，6-秒，不填默认为日
     * @param date1    Date型日期
     * @param date2    Date型日期
     * @return          间隔的数量。如果日期date2在日期date1之后，则结果为正数；如果日期date2在日期date1之前，则结果为负数
     */
    public static int dateDiff(int type, Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        if (type == DATATYPE_YEAR)
        {//年
            return yearDiff;
        }
        else if (type == DATATYPE_MONTH)
        {//月
            int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
            return monthDiff;
        }
        else
        {
            long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
            long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
            if (type == DATATYPE_HOUR)
            {//小时
                return (int) ((ldate2 - ldate1) / 3600000);
            }
            else if (type == DATATYPE_MINUTE)
            {//分钟
                return (int) ((ldate2 - ldate1) / 60000);
            }
            else if (type == DATATYPE_SECOND)
            {//秒
                return (int) ((ldate2 - ldate1) / 1000);
            }
            else
            {//日
                return (int) ((ldate2 - ldate1) / (3600000 * 24));
            }
        }
    }

    /**
     * 计算日期加上一段间隔之后的新日期
     * @param type  间隔的单位：1-年，2-月，3-日，4-小时，5-分钟，6-秒，不填默认为日
     * @param date  日期
     * @param num   间隔数量
     * @return      返回新日期
     * @throws Exception
     */
    public static Date dateAdd(int type, Date date, int num)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (type == DATATYPE_YEAR)
        {
            cal.add(Calendar.YEAR, num);
        }
        else if (type == DATATYPE_MONTH)
        {
            cal.add(Calendar.MONTH, num);
        }
        else if (type == DATATYPE_HOUR)
        {
            cal.add(Calendar.HOUR, num);
        }
        else if (type == DATATYPE_MINUTE)
        {
            cal.add(Calendar.MINUTE, num);
        }
        else if (type == DATATYPE_SECOND)
        {
            cal.add(Calendar.SECOND, num);
        }
        else
        {
            cal.add(Calendar.DATE, num);
        }
        return cal.getTime();
    }

    /**
     * 计算日期加上一段间隔之后的新日期
     * @param type      间隔的单位：1-年，2-月，3-日，4-小时，5-分钟，6-秒，不填默认为日
     * @param sdate     String型日期
     * @param num       间隔数量
     * @return          返回新日期
     */
    public static String dateAdd(int type, String sdate, int num) throws Exception
    {
        Date date = DateUtils.parseDate(sdate, getFormat(sdate));
        Date newDate = dateAdd(type, date, num);
        return DateFormatUtils.format(newDate, getFormat(sdate));
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param ts
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Timestamp ts) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        try {
            date = ts;
            System.out.println(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 计算年龄
     * 1、对于天，两个日期直接相减
     * 2、对于月，如果2011年3月26日出生，则2011年4月27日才满一个月
     * 3、对于周岁，如果2010年4月26日出生，则2011年4月27日才满一周岁
     * @param birthday  出生日期
     * @param type      年龄类型，Y-周岁，M-月，D-天，默认为周岁
     * @return          年龄数值
     * @throws Exception
     */
    public static int calAge(String birthday, String type) throws Exception
    {
        String currDate = getCurrentDate();
        if ("D".equalsIgnoreCase(type))
        {
            return dateDiff(DATATYPE_DAY, birthday, currDate);
        }
        else if ("M".equalsIgnoreCase(type))
        {
            int result = dateDiff(DATATYPE_MONTH, birthday, currDate);
            String temp = dateAdd(DATATYPE_MONTH, birthday, result);
            if (dateDiff(DATATYPE_DAY, temp, currDate) <= 0)
            {
                result--;
            }
            return result;
        }
        else
        {
            int result = dateDiff(DATATYPE_YEAR, birthday, currDate);
            String temp = dateAdd(DATATYPE_YEAR, birthday, result);
            if (dateDiff(DATATYPE_DAY, temp, currDate) <= 0)
            {
                result--;
            }
            return result;
        }
    }

    /**
     * 获得本年1月1号零点的日期时间
     * @return
     * @throws Exception
     */
    public static Date getFirstDayOfYear() throws Exception
    {
        return convertStringToDate(getCurrentDateTime("yyyy") + "-01-01 00:00:00");
    }

    /**
     * 获取本月1号零点的日期时间
     * @return
     * @throws Exception
     */
    public static Date getFirstDayOfMonth() throws Exception
    {
        return convertStringToDate(getCurrentDateTime("yyyy-MM") + "-01 00:00:00");
    }

    /**
     * 获取本周第一天（周日）零点的日期时间
     * @return
     * @throws Exception
     */
    public static Date getMondayOfDate() throws Exception
    {
        Date date = new Date();
        int x = getWeekOfDate(date);
        date = dateAdd(DATATYPE_DAY, date, -x);
        return getZeroHourOfDate(date);
    }

    /**
     * 获取某天凌晨时间
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getZeroHourOfDate(Date date) throws Exception
    {
        return convertStringToDate(convertDateToString(date, YYYY_MM_DD) + " 00:00:00");
    }

    /**
     * 获取日期是星期几
     * @param date  日期
     * @return      0-周日，1-周一，2-周二。。。。。。
     */
    public static int getWeekOfDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
        {
            w = 0;//0表示星期天，是一周的第一天
        }
        return w;
    }

    /**
     * 获取指定日期所在月份的天数
     * @param dt
     * @return
     */
    public static Integer getCountOfMonth(Date dt)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取指定日期所在月份第一天是星期几
     * @param dt 日期
     * @return  0-周日，1-周一，2-周二。。。。。。
     */
    @SuppressWarnings("deprecation")
    public static Integer getWeekOfMonthFirstDay(Date dt)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(dt.getYear() + 1900, dt.getMonth(), 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 正则验证日期格式(yyyy-MM-dd)
     * @param dt 日期
     */
    public static boolean dateMatcher(String dt)
    {
        Pattern pattern = Pattern.compile("((((0[0-9])(([02468][48])|([13579][26]))-0?2-29))|(((0[0-9])(([2468][048])|([13579][26]))-0?2-29))|(((0[1-9])(([02468][048])|([13579][26]))-0?2-29))|((([1-9][0-9])(([02468][048])|([13579][26]))-0?2-29))|((0[0-9][0-9][1-9])|(0[0-9][1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0?[1-9])|(1[0-2]))-((0?[1-9])|(1[0-9])|(2[0-8])))|((((0?[13578])|(1[02]))-31)|(((0?[1,3-9])|(1[0-2]))-(29|30)))))");
        Matcher matcher = pattern.matcher(dt);
        return matcher.matches();
    }

    /**
     * 正则验证日期格式(yyyy-MM-dd hh:mm:ss)
     * @param dt 日期
     */
    public static boolean dateTimeMatcher(String dt)
    {
        Pattern pattern = Pattern.compile("^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$");
        Matcher matcher = pattern.matcher(dt);
        return matcher.matches();
    }

    /**
     * 获取某一天前或后指定天（列：days=1时获取前天）
     * @param date 某一天
     * @param format 返回时间类型
     * @param days 天数(正数表示后几天负数表示前几天：-1为取前一天,1为取后一天)
     * @return String (yyyy-MM-dd)
     */
    public static String getBeforeOrAfterDay(Date date, String format, Integer days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return DateFormatUtils.format(calendar.getTime(), format);
    }

    /**
     * 获取某月的第一天和最后一天
     * @param moons 某一月
     * @param returnType 返回类型(firstDay第一天,lastDay最后一天)
     * @return Map (firstDay,lastDay) 默认返回最后一天
     */
    public static String getFirstOrLastDay(String moons, String returnType)
    {
        String day = null;
        String[] monthArr = moons.split("-");
        Integer year = Integer.parseInt(monthArr[0]);
        Integer month = Integer.parseInt(monthArr[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDay = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDay = calendar.getTime();
        if ("firstDay".equalsIgnoreCase(returnType))
        {
            day = TimeUtils.convertDateToString(firstDay, TimeUtils.YYYY_MM_DD);
        }
        else
        {
            day = TimeUtils.convertDateToString(lastDay, TimeUtils.YYYY_MM_DD);
        }
        return day;
    }

    /**
     * 获取定时器的启动日期和时间
     * 规则：time为传入的时间，
     * 如果当前时间大于time，则定时器下一次启动时间为第二天的time时刻，
     * 否则，定时器下一次启动时间为当前的time时刻
     * @param time  启动时间（HH:mm:ss）
     * @return      启动日期和时间
     * @throws Exception
     */
    public static Date getTimerDate(String time) throws Exception
    {
        String reg = "\\d{2}:\\d{2}:\\d{2}";
        if (time.matches(reg))
        {
            String[] temp = time.split(":");
            Calendar c = Calendar.getInstance();
            Date now = new Date();
            c.setTime(now);
            c.set(Calendar.HOUR, Integer.parseInt(temp[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(temp[1]));
            c.set(Calendar.SECOND, Integer.parseInt(temp[2]));
            Date timer = c.getTime();
            if (timer.after(now))
            {
                return timer;
            }
            else
            {
                c.add(Calendar.DAY_OF_YEAR, 1);
                return c.getTime();
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如(今天)|(明天)|(后天)
     *
     * @param timeStamp
     * @return (今天)|(明天)|(后天)|""
     */
    public static String convertTimeToFormat(long timeStamp) {
        Calendar today = Calendar.getInstance();
        Calendar old = Calendar.getInstance();

        old.setTimeInMillis(timeStamp);
        int yearOld = old.get(Calendar.YEAR);    //获取年
        int monthOld = old.get(Calendar.MONTH) + 1;   //获取月份
        int dayOld = old.get(Calendar.DAY_OF_MONTH);    //获取天数

        int yearToday = today.get(Calendar.YEAR);
        int monthToday = today.get(Calendar.MONTH) + 1;
        int dayToday = today.get(Calendar.DAY_OF_MONTH);
        if (yearOld == yearToday && monthOld == monthToday) {
            if (dayToday == dayOld) {
                return "(今天)";
            } else if (dayToday + 1 == dayOld) {
                return "(明天)";
            } else if (dayToday + 2 == dayOld) {
                return "(后天)";
            }
        }
        return "";
    }

    /**
     * 比较两个时间是否为次日
     *
     * @param beginTimeStap
     * @param endTimeStamp
     * @return (次日)
     */
    public static String convertTimeStr(long beginTimeStap,long endTimeStamp) {
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        beginTime.setTimeInMillis(beginTimeStap);
        endTime.setTimeInMillis(endTimeStamp);
        int yearOld = endTime.get(Calendar.YEAR);    //获取年
        int monthOld = endTime.get(Calendar.MONTH) + 1;   //获取月份
        int dayOld = endTime.get(Calendar.DAY_OF_MONTH);    //获取天数

        int yearToday = beginTime.get(Calendar.YEAR);
        int monthToday = beginTime.get(Calendar.MONTH) + 1;
        int dayToday = beginTime.get(Calendar.DAY_OF_MONTH);
        if (yearOld == yearToday && monthOld == monthToday) {
           if (dayToday + 1 == dayOld) {
                return "(次日)";
            }
        }
        return "";
    }

    /**
     * 获取两个日期的相差天数,如果结束日期小于开始日期，返回0
     * @param beginTimeStap
     * @param endTimeStamp
     * @return
     */
    public static long getBeginAndEndTimeDays(long beginTimeStap,long endTimeStamp) {
        long days = 0;
        if (endTimeStamp > beginTimeStap) {
            long diff = endTimeStamp - beginTimeStap;
            days = diff / (1000 * 60 * 60 * 24);
            if (days == 0) {
                days = 1;
            }
        }
       return days;
    }

    public static boolean timeBetweenCheck(Timestamp timestamp, String begin, String end) {
        if (begin == null || end == null) {
            return false;
        }

        LocalTime localTime = timestamp.toLocalDateTime().toLocalTime();
        LocalTime beginTime = LocalTime.parse(begin);
        LocalTime endTime = LocalTime.parse(end);

        return localTime.equals(beginTime) || localTime.equals(endTime)
                || (localTime.isAfter(beginTime) && localTime.isBefore(endTime));
    }

}
