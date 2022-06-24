package com.newcore.laboratory.utils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 排序算法工具类
 * @author zhouchaowei
 */
public class SortAlgorithmUtils {

    private static SortAlgorithmUtils sortAlgorithmUtilsInstance;

    private SortAlgorithmUtils(){

    }

    /**
     * 通过单例获取算法工具类实例
     * @return
     */
    public static synchronized SortAlgorithmUtils getSortAlgorithmUtilsInstance(){
        if(sortAlgorithmUtilsInstance == null){
            sortAlgorithmUtilsInstance = new SortAlgorithmUtils();
        }
        return sortAlgorithmUtilsInstance;
    }

    /**
     * 1.归并排序
     * @param array 待排序的数组
     * @param lowElement 待排序数组中最小的元素
     * @param highElement 待排序数组中最大的元素
     * @return
     */
    public int[] mergeSort(int[] array,int lowElement, int highElement){
        int middle = (lowElement + highElement) / 2;
        if(lowElement < highElement){
            mergeSort(array, lowElement, middle);
            mergeSort(array, middle + 1, highElement);

            /** 以下是归并算法核心逻辑 */
            int[] temp = new int[highElement - lowElement + 1];
            int i = lowElement;
            int j = middle + 1;
            int k = 0;
            while(i <= middle && j <= highElement){
                if(array[i] < array[j]){
                    temp[k++] = array[i++];
                }else{
                    temp[k++] = array[j++];
                }
            }

            while(i <= middle){
                temp[k++] = array[i++];
            }

            while(j <= highElement){
                temp[k++] = array[j++];
            }

            for(int x = 0;x < temp.length;x++){
                array[x + lowElement] = temp[x];
            }
        }
        return array;
    }

    /**
     * 2.插入排序
     * @param array 待排序的数组
     * @return
     */
    public int[] insertionSort(int[] array){
        for(int i = 1;i < array.length; i++){
            for(int j = i;j > 0;j--){
                if(array[j] < array[j - 1]){
                    int temp = array[j -1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 3.冒泡排序
     * @param array 待排序的数组
     * @return
     */
    public int[] bubbleSort(int[] array){
        int temp = 0;
        for(int i = 0;i < array.length - 1;i++){
            for(int j = 0;j < array.length - 1;j++){
                if(array[j] > array[j + 1]){
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 4.希尔排序
     * @param array 待排序的数组
     * @return
     */
    public int[] shellSort(int[] array){
        for(int step = array.length / 2;step > 0;step /= 2){
            for(int index = step;index < array.length;index++){
                int value = array[index];
                int temp;
                for(temp = index - step;temp >= 0 && array[temp] > value;temp = step){
                    array[temp + step] = array[temp];
                }
                array[temp + step] = value;
            }
        }
        return array;
    }

    /**
     * 5.选择排序
     * @param array 待排序的数组
     * @return
     */
    public int[] selectSort(int[] array){
        return array;
    }

    /**
     * 6.快速排序
     * @param array 待排序的数组
     * @return
     */
    public int[] quickSort(int[] array){
        return array;
    }

    /**
     * 7.基数排序
     * @param array 待排序的数组
     * @return
     */
    public int[] radixSort(int[] array){
        return array;
    }

    /**
     * 8.堆排序
     * @param array 待排序的数组
     * @return
     */
    public int[] heapSort(int[] array){
        return array;
    }

    /**
     * 9.对日期集合进行正序排序
     * @param dateList 待排序的日期集合
     */
    public void sortListPositiveSequence(List<Date> dateList){
        dateList.sort(new Comparator<Date>() {
            /** 正序排列 */
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * 10.对日期集合进行倒序排序
     * @param dateList 待排序的日期集合
     */
    public void sortListReverseOrder(List<Date> dateList){
        dateList.sort(new Comparator<Date>() {
            /** 正序排列 */
            @Override
            public int compare(Date o1, Date o2) {
                return o2.compareTo(o1);
            }
        });
    }
}
