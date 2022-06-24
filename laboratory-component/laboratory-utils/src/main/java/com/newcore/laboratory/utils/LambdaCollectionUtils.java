package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lambda表达式工具类
 * @author zhouchaowei
 */
public class LambdaCollectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(LambdaCollectionUtils.class);

    /**
     * 截取集合前n个元素,并返回新集合
     * @param list 需要截取的集合
     * @param indexNum 截取元素个数
     * @param <T>
     * @return
     */
    public <T> List<T> firstIndex(List<T> list,int indexNum){
        return list.stream().limit(indexNum).collect(Collectors.toList());
    }
}
