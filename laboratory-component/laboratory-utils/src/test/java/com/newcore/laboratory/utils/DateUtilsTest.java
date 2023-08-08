package com.newcore.laboratory.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtilsTest {

    @Test
    public void test_calculateMonthMinus() throws ParseException {
        /* 包含闰年的情况 */
        System.out.println("包含闰年的情况：");
        Date startDate_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-06-01 00:00:00");
        Date endDate_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2025-06-01 00:00:00");
        calculateMonthMinus(startDate_1,endDate_1);

        System.out.println("-----------------------------------");
        /* 不包含闰年的情况 */
        System.out.println("不包含闰年的情况：");
        Date startDate_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2025-06-01 00:00:00");
        Date endDate_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2027-06-01 00:00:00");
        calculateMonthMinus(startDate_2,endDate_2);

        System.out.println("-----------------------------------");
        /* 生产未触发规则实际情况 */
        /**
         * 2021-04-29 ~ 2023-04-18 719天,转换成:1年11月18天
         * 2021-05-19 ~ 2023-05-14 725天,转换成:1年11月24天
         * 2021-05-25 ~ 2023-05-18 723天,转换成:1年11月22天
         * 2021-04-16 ~ 2023-04-09 723天,转换成:1年11月22天
         * 2021-05-31 ~ 2023-05-07 706天,转换成:1年11月5天
         * 2021-05-31 ~ 2023-05-20 719天,转换成:1年11月18天
         */
        System.out.println("生产未触发规则实际情况：");
        Date startDate_3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-07-20 00:00:00");
        Date endDate_3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-07-20 00:00:00");
        calculateMonthMinus(startDate_3,endDate_3);

        System.out.println("-----------------------------------");
        /* 二月份的情况 */
        System.out.println("二月份的情况：");
        Date startDate_4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-02-29 00:00:00");
        Date endDate_4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-04-01 00:00:00");
        calculateMonthMinus(startDate_4,endDate_4);
    }

    /**
     * 计算两个日期相隔的天数-新方法
     * @param signingDate
     * @param currentDate
     */
    private static void calculateMonthMinus(Date signingDate,Date currentDate){
        String signingDateStr = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(signingDate);
        String currentDateStr = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(currentDate);

        String signingDateStrPre = signingDateStr.split("_")[0];
        String currentDateStrPre = currentDateStr.split("_")[0];

        System.out.println("起始日期:" + signingDateStrPre);
        System.out.println("截止日期:" + currentDateStrPre);

        int startYear = Integer.parseInt(signingDateStrPre.split("-")[0]);
        int startMonth = Integer.parseInt(signingDateStrPre.split("-")[1]);
        int startDay = Integer.parseInt(signingDateStrPre.split("-")[2]);

        int endYear = Integer.parseInt(currentDateStrPre.split("-")[0]);
        int endMonth = Integer.parseInt(currentDateStrPre.split("-")[1]);
        int endDay = Integer.parseInt(currentDateStrPre.split("-")[2]);

        LocalDateTime start = LocalDateTime.of(startYear, startMonth, startDay, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(endYear, endMonth, endDay, 0, 0, 0);

        Duration duration = Duration.between(start,end);
        long days = duration.toDays();
        calculateMonth(days);
        System.out.println("两个日期相隔的天数为:" + days);
    }

    /**
     * 将天数转换为月份数
     * @param days
     * @return
     */
    private static String calculateMonth (long days){
        long year = days / 365;  //年
        long month;              //月
        long day;                //日
        long monthDays = days - (year * 365); //除去年份后剩余天数
        int monthNum = 0;  //月份
        while (monthDays > 0) {  //剩余天数计算月份
            monthNum++;
            //前7个月 为奇数的是31天 后5个月 为偶数的是31天
            if (monthNum <= 7 && (monthNum & 1) != 0 || (monthNum > 7 && (monthNum & 1) == 0)) {
                if ((monthDays - 31) < 0) {
                    monthNum--;
                    break;
                }
                monthDays -= 31;
            } else if (monthNum <= 7 && (monthNum & 1) == 0
                    || (monthNum > 7 && (monthNum & 1) != 0)) {
                //前7个月 为偶数的是30天 后5个月 为奇数的是30天
                if ((monthDays - 30) < 0) {
                    monthNum--;
                    break;
                }
                monthDays -= 30;
            }
        }
        if (monthNum <= 0) {  //若月份小于等于0,代表剩余天数不满足一个月的天数,则月份为0
            month = 0;
            day = days - (year * 365);
        } else {
            month = monthNum;
            day = monthDays;
        }
        String time = (year > 0 ? (year + "年") : "0年") + (month > 0 ? (month + "月") : "0月") + (day > 0 ? day + "天" : "0天");
        System.out.println(days + "天,转换成:" + time);

        long timeMonth = (year > 0 ? year * 12 : 0) + (month > 0 ? month : 0);
        System.out.println("月份数：" + timeMonth);
        return time;
    }
}
