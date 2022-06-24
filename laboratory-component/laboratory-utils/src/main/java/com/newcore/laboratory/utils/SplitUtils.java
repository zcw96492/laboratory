package com.newcore.laboratory.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 切分工具类
 * @author zhouchaowei
 */
public class SplitUtils {

    private static SplitUtils splitUtilsInstance;

    private static final Logger logger = LoggerFactory.getLogger(SplitUtils.class);

    private SplitUtils(){

    }

    /**
     * 通过单例获取算法工具类实例
     * @return
     */
    public static synchronized SplitUtils getSplitUtilsInstance(){
        if(splitUtilsInstance == null){
            splitUtilsInstance = new SplitUtils();
        }
        return splitUtilsInstance;
    }

    /**
     * 用特定的符号将集合转换成指定符号分割形式(不指定符号时,默认用逗号分割)
     * @param list 要转换测数据集合
     * @param symbol 分隔符号
     * @param <T> 集合泛型
     * @return
     */
    public <T> String splitWithSymbol(List<T> list,String symbol){
        Assert.notEmpty(list,"list should be not null!!!");
        String splitStr = list.stream().map(String::valueOf).collect(Collectors.joining(StringUtils.isBlank(symbol) ? "," : symbol));
        logger.info("分割处理后的字符串为:{}",splitStr);
        return splitStr;
    }

    /**
     * 根据分隔符号,将分割字符串拆成集合
     * @param str 待分割字符串
     * @param symbol 分割符号
     * @return
     */
    public List<String> splitBySymbol(String str,String symbol){
        return Arrays.asList(str.split(symbol));
    }
}
