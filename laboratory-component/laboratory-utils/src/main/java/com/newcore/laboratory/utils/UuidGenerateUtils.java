package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * UUID生成工具类
 * @author zhouchaowei
 * @since 2019.10.25  15:35
 */
public class UuidGenerateUtils {

    private static Logger logger = LoggerFactory.getLogger(UuidGenerateUtils.class);

    /**
     * 生成UUID方法
     * @param connectorFlag 是否包含连接符 true-包含连接符  false-不包含连接符
     * @param toUpperCaseFlag 是否转大写 true-开启转大写  false-关闭转大写
     * @return
     */
    public static String generateUUID(boolean connectorFlag, boolean toUpperCaseFlag){
        if(connectorFlag){
            if(toUpperCaseFlag){
                return UUID.randomUUID().toString().toUpperCase();
            }else{
                return UUID.randomUUID().toString();
            }
        }else{
            if(toUpperCaseFlag){
                return UUID.randomUUID().toString().replace("-","").toUpperCase();
            }else{
                return UUID.randomUUID().toString().replace("-","");
            }
        }
    }
}
