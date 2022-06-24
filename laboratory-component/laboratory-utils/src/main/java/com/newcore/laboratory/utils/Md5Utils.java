package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author zhouchaowei
 */
public class Md5Utils {

    private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    private static final String [] HEX_DIGITS = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetName 编码格式
     * @return
     */
    public static String MD5Encode(String origin, String charsetName){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetName || "".equals(charsetName)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
                logger.info("加密后的字符串为:{}",resultString);
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
                logger.info("按照{}格式加密后的字符串为:{}",charsetName,resultString);
            }
        }catch (Exception e){
            logger.error("MD5加密出错",e);
        }
        return resultString;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String byteArrayToHexString(byte [] bytes){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++){
            resultSb.append(byteToHexString(bytes[i]));
        }
        return resultSb.toString();
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte bytes){
        int n = bytes;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
