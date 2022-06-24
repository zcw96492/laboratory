package com.newcore.laboratory.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 一些比较功能工具类
 * @author zhouchaowei
 */
public class CompareUtils {

    /**
     * 从小到大排好序的列表中找第一个符合的
     * @param current 当前值
     * @param sortedList 排好序的列表
     * @param <T> T
     */
    public static <T extends Comparable<T>> T findNext(T current , List<T> sortedList){
        for (T item : sortedList) {
            if(item.compareTo(current) >= 0){
                return item;
            }
        }
        throw new IllegalArgumentException("超出范围了");
    }

    /**
     *
     * @param num
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean inList(T num, List<T> list) {
        for (T tmp : list) {
            if (tmp.equals(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 利用Set列表去重,要求<T>必须实现hashCode和equals方法
     * @param list
     * @param <T>
     */
    public static <T> void removeDuplicate(Collection<T> list) {
        LinkedHashSet<T> set = new LinkedHashSet<>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }

    /**
     * 比较大小,左边的必须比右边小
     * @param left
     * @param right
     */
    public static void assertSize(int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("right should bigger than left , but find " + left + " > " + right);
        }
    }
}
