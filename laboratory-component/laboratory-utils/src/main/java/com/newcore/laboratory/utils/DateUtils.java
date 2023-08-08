package com.newcore.laboratory.utils;

import com.newcore.laboratory.utils.enumclass.BusinessExceptionCodeEnum;
import com.newcore.laboratory.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * @author zhouchaowei
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final Calendar calendar = Calendar.getInstance();

    /**
     * 获取day天前的指定时间
     * (例如:生成当前时间前一天时间零点，当前时间为2016/7/18 15:44:30，则生成时间为2016/7/17 00:00:00)
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 日期类型
     */
    public static Date getBeforeDayTimeReturnDate(int day,int hour,int minute,int second){
        setCalendarTime(day,hour,minute,second);
        return calendar.getTime();
    }

    /**
     * 获取当前时间日期
     * @param currentDateStr 当前时间日期字符串
     * @param pattern 模板
     * @return
     */
    public static Date getCurrentDate(String currentDateStr, String pattern){
        try {
            return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern).parse(currentDateStr);
        } catch (ParseException e) {
            throw new BusinessException(BusinessExceptionCodeEnum.FAIL.getCode(),BusinessExceptionCodeEnum.FAIL.getMessage());
        }
    }

    /**
     * 获取day天前的指定时间，返回标准格式的(2016-7-18 15:44:30)
     * (例如:生成当前时间前一天时间零点，当前时间为2016/7/18 15:44:30，则生成时间为2016/7/17 00:00:00)
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 字符串类型
     */
    public static String getBeforeDayTimeReturnString(int day,int hour,int minute,int second){
        setCalendarTime(day,hour,minute,second);
        return getFormatDateString(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间若干小时之前的时间点
     * @param hour 小时数
     * @return 时间类型
     */
    public static Date getBeforeHourTimeReturnDate(int hour){
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return calendar.getTime();
    }

    /**
     * 将Date类型的日期按指定格式输出
     * @param date 要格式化的日期
     * @param format 格式化模板
     * @return
     */
    public static String getFormatDateString(Date date,String format){
        if(date == null){
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取格式化的当前日期(不带时分秒)
     * @return
     */
    public static String getCurrentDateTimeOnlyYearMonthDay(){
        return getFormatDateString(new Date(),"yyyy-MM-dd");
    }

    /**
     * 获取格式化的当前日期(带时分秒)
     * @return
     */
    public static String getCurrentDateTime(){
        return getFormatDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 设定日期方法
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     */
    private static void setCalendarTime(int day,int hour,int minute,int second){
        calendar.setTime(new Date());
        calendar.set(Calendar. HOUR_OF_DAY, hour < 0 || hour > 24 ? 0 : hour);
        calendar.set(Calendar. MINUTE, minute < 0 || minute > 60 ? 0 : minute);
        calendar.set(Calendar. SECOND, second < 0 || second > 60 ? 0 : second);
        calendar.set(Calendar. MILLISECOND, 0);
        calendar.add(Calendar. DAY_OF_MONTH, Integer.valueOf("-" + (day < 0 ? 0 : day)));
    }

    /**
     * 判断某一天是否在指定日期之前若干天和之后若干天之间
     * @param date 要判断的某一天
     * @param assignDate 指定的日期
     * @param beforeDays 指定日期之前的若干天
     * @param afterDays 指定日期之后的若干天
     * @return
     */
    public static boolean checkCurrentTimeBetweenTwoDays(Date date, String assignDate, int beforeDays, int afterDays) {
        Calendar beforeDayTime = Calendar.getInstance();
        Calendar afterDayTime = Calendar.getInstance();
        try{
            beforeDayTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(assignDate));
            afterDayTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(assignDate));
        }catch (ParseException e){
            throw new BusinessException("日期转换异常");
        }
        beforeDayTime.add(Calendar.DAY_OF_MONTH, beforeDays);
        afterDayTime.add(Calendar.DAY_OF_MONTH, -afterDays);

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);

        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(beforeDayTime.getTime());

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(afterDayTime.getTime());

        return currentDate.after(beginDate) && currentDate.before(endDate);
    }

    /**
     * 判断当前时间是否在指定日期之前若干天和之后若干天之间
     * @param date 指定的日期
     * @param beforeDays 指定日期之前的若干天
     * @param afterDays 指定日期之后的若干天
     * @return
     */
    public static boolean checkCurrentTimeBetweenTwoDays(String date, int beforeDays, int afterDays) {
        return checkCurrentTimeBetweenTwoDays(new Date(),date,beforeDays,afterDays);
    }

    /**
     * 获取指定日期的前一天
     * @param specifiedDay 指定日期(字符串)
     * @return 字符串类型的日期
     */
    public static String getSpecifiedDayBefore(String specifiedDay){
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE,day-1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取指定日期的前一天
     * @param specifiedDay 指定日期(字符串)
     * @return 日期类型的日期
     */
    public static Date getSpecifiedDayBeforeReturnDate(String specifiedDay){
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE,day-1);
        return calendar.getTime();
    }

    /**
     * 判断某一日期是否在当前时间前若干天之前
     * [举个例子:即判断2020-10-12 是否在当前时间前5天之前,如当前时间是:2020-11-04,也就是判断2020-10-12 是否在 2020-10-30之前]
     * @param dayNum 几天前的天数
     * @return 日期类型的日期
     */
    public static boolean checkTimeBeforeAssignDate(String specificDate,int dayNum){
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE,day - dayNum);
        Date time = calendar.getTime();
        logger.info("当前时间的前{}天的时间是:{}", dayNum ,new SimpleDateFormat("yyyy-MM-dd").format(time));

        Calendar currentDate = Calendar.getInstance();
        try {
            currentDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specificDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate.before(calendar);
    }

    /**
     * 将SQL类型的时间戳转换为标准日期格式的字符串
     * @param timestamp SQL类型的时间戳
     * @param format 日期格式
     * @return
     */
    public static String sqlTimeStampToDateStr(Timestamp timestamp, String format) {
        return new SimpleDateFormat(
                StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format
        ).format(new Date(timestamp.getTime()));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期
     * @param format 日期格式
     * @return
     */
    public static String dateStrToSqlTimeStamp(String dateStr, String format){
        try {
            return new Timestamp(new SimpleDateFormat(
                    StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format
            ).parse(dateStr).getTime()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取得SQL类型的时间戳
     * @param date 时间
     * @return
     */
    public static Timestamp getSqlTimeStamp(Date date){
        return new Timestamp(date.getTime());
    }

    /**
     * 计算两个时间之间的月份差(返回的是月份的差值)
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getPeriodMonth(Date startDate, Date endDate){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 计算年份差
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 年份差
     */
    public static Integer calculateYearMinus(Date startDate , Date endDate) throws ParseException {
        if(startDate == null || endDate == null){
            return 0;
        }

        String startDateStr = getFormatDateString(startDate,"yyyy-MM-dd").replaceAll("[\\/\\-\\.]","");
        String endDateStr = getFormatDateString(endDate,"yyyy-MM-dd").replaceAll("[\\/\\-\\.]","");

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        Date beginDateFormat = df.parse(startDateStr);
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDateFormat);

        Date endDateFormat = df.parse(endDateStr);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDateFormat);

        int beginYear = beginCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int minusYear = endYear - beginYear;

        int beginMonth = beginCalendar.get(Calendar.MONTH);
        int endMonth = endCalendar.get(Calendar.MONTH);
        int minusMonth = endMonth - beginMonth;

        int beginDayOfMonth = beginCalendar.get(Calendar.DAY_OF_MONTH);
        int endDayOfMonth = endCalendar.get(Calendar.DAY_OF_MONTH);
        int minusDay = endDayOfMonth - beginDayOfMonth;

        /* 判断起期与止期是否是闰年 */
        String beginLeapYear = "N", endLeapYear = "N";
        if(beginYear % 4 == 0 && beginYear% 100 != 0 || beginYear % 400 == 0){
            beginLeapYear = "Y";
        }
        if(endYear % 4 == 0 && endYear% 100 != 0 || endYear % 400 == 0){
            endLeapYear = "Y";
        }

        if("Y".equals(beginLeapYear) && beginMonth + 1 == 2 && beginDayOfMonth == 29 &&
                "N".equals(endLeapYear) && endMonth + 1 == 3 && endDayOfMonth == 1){
            /* TODO 起期为闰年的2月29号,止期为非闰年的3月1日,不处理 */
        }else if((minusMonth < 0 || (minusMonth == 0 && minusDay < 0)) && (minusYear == 5 || minusYear == 10 || minusYear == 20)){
            minusYear = minusYear - 1;
        }else if((minusMonth > 0 || minusMonth == 0 && minusDay > 0) && (minusYear == 5 || minusYear == 10 || minusYear == 20)){
            minusYear = minusYear + 1;
        }
        return minusYear;
    }

    /**
     * 计算两个时间点的月份差
     * @param signingDate 销售员签约日期
     * @param currentDate 当前日期
     */
    public static void calculateMonthMinus(Date signingDate,Date currentDate){

        LocalDateTime start = LocalDateTime.of(2023,1,1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023,1,15, 0, 0, 0);
        Duration duration = Duration.between(start,end);
        long minus = duration.toDays();
        System.out.println("两个日期相隔的天数为:" + minus);
    }
}











