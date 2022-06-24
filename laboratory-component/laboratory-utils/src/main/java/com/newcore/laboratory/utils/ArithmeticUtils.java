package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数学类、财务计算工具类
 * @author zhouchaowei
 */
public class ArithmeticUtils {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticUtils.class);

    private ArithmeticUtils(){

    }

    /**
     * 将两个字符串类型的数字相加(带四舍五入功能)
     * @param num1 被加数
     * @param num2 加数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相加的和
     */
    public static String add(String num1,String num2,int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相加,保留 {} 位小数",num1,num2,decimalPlace);
        return new BigDecimal(num1).add(new BigDecimal(num2)).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相加(带四舍五入功能)
     * @param num1 被加数
     * @param num2 加数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相加的和
     */
    public static String add(BigDecimal num1,BigDecimal num2,int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相加,保留 {} 位小数",num1,num2,decimalPlace);
        return num1.add(num2).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相减(带四舍五入功能)
     * @param num1 被减数
     * @param num2 减数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相减的差
     */
    public static String subtract(String num1,String num2,int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相减,保留 {} 位小数",num1,num2,decimalPlace);
        return new BigDecimal(num1).subtract(new BigDecimal(num2)).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相减(带四舍五入功能)
     * @param num1 被减数
     * @param num2 减数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相减的差
     */
    public static String subtract(BigDecimal num1,BigDecimal num2,int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相减,保留 {} 位小数",num1,num2,decimalPlace);
        return num1.subtract(num2).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相乘(带四舍五入功能)
     * @param num1 乘数1
     * @param num2 乘数2
     * @param decimalPlace 保留小数点后面的位数
     * @return 相乘的积
     */
    public static String multiply(String num1, String num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相乘,保留 {} 位小数",num1,num2,decimalPlace);
        return new BigDecimal(num1).multiply(new BigDecimal(num2)).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相乘(带四舍五入功能)
     * @param num1 乘数1
     * @param num2 乘数2
     * @param decimalPlace 保留小数点后面的位数
     * @return 相乘的积
     */
    public static String multiply(BigDecimal num1, BigDecimal num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相乘,保留 {} 位小数",num1,num2,decimalPlace);
        return num1.multiply(num2).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相除(带四舍五入功能，当发生除不尽的情况时，由scale参数指定精度)
     * @param num1 被除数
     * @param num2 除数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相除的商
     */
    public static String divide(String num1, String num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相除,保留 {} 位小数",num1,num2,decimalPlace);
        return new BigDecimal(num1).divide(new BigDecimal(num2), decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将两个字符串类型的数字相除(带四舍五入功能，当发生除不尽的情况时，由scale参数指定精度)
     * @param num1 被除数
     * @param num2 除数
     * @param decimalPlace 保留小数点后面的位数
     * @return 相除的商
     */
    public static String divide(BigDecimal num1, BigDecimal num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相除,保留 {} 位小数",num1,num2,decimalPlace);
        return num1.divide(num2, decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 取余数
     * @param num1 被除数
     * @param num2 除数
     * @param decimalPlace 保留小数点后面的位数
     * @return 余数
     */
    public static String remainder(String num1, String num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相除,余数保留 {} 位小数",num1,num2,decimalPlace);
        return new BigDecimal(num1).remainder(new BigDecimal(num2)).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 取余数
     * @param num1 被除数
     * @param num2 除数
     * @param decimalPlace 保留小数点后面的位数
     * @return 余数
     */
    public static String remainder(BigDecimal num1, BigDecimal num2, int decimalPlace){
        if (decimalPlace < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        logger.info("将 {} 与 {} 相除,余数保留 {} 位小数",num1,num2,decimalPlace);
        return num1.remainder(num2).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 为财务值保留小数位数的方法
     * @param scale 保留小数的位数
     * @param bigDecimal 需要保留小数位数的值
     * @return 保留小数位数后的数据
     */
    public static BigDecimal getScaleCustom(int scale,BigDecimal bigDecimal){
        return bigDecimal.setScale(scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将一个BigDecimal类型的集合中所有的值累加
     * @param list 要合计的集合
     * @return 和
     */
    public static BigDecimal getSumAmountFromList(List<BigDecimal> list){
            return list.stream().map(item -> item == null ? BigDecimal.ZERO : item).
                    reduce(BigDecimal.ZERO, BigDecimal :: add).
                    setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 财务值比较大小方法【返回true的话,args_1 的值比 args_2 的值要小】
     * @param args_1 要比较的财务值
     * @param args_2 被比较的财务值
     * @return 布尔值
     */
    public static boolean compareToFinanceValue(BigDecimal args_1,BigDecimal args_2){
        return args_1.compareTo(args_2) == -1;
    }
}
