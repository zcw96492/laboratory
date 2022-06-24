package com.newcore.laboratory.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 线程池并发查询工具类
 * @author zhouchaowei
 */
public class ConcurrentQueryUtils {

    /** 日志门面 */
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentQueryUtils.class);

    /** 线程池的基本大小 */
    private static final int CORE_POOL_SIZE = 6;

    /** 线程池中允许的最大线程数 */
    private static final int MAXIMUM_POOL_SIZE = 150;

    /** 线程空闲状态超时退出时间最大值 */
    private static final long KEEP_ALIVE_TIME = 0L;

    /** 并发队列容量 */
    private static final int CAPACITY = 1024;

    /**
     * 核心查询方法
     * @param callableList 要进行并发查询的并发任务集合
     * @param threadPoolName 查询的线程池名称
     * @param <T> 返回集合的泛型
     * @return
     */
    public static <T> List<T> query(List<Callable<T>> callableList,String threadPoolName) throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-pool-d%").build();
        logger.info("线程池并发查询工具类 || 开始创建线程池 || 线程列表:{} || 线程池名称:{}",callableList,threadPoolName);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                Math.min(callableList.size(), CORE_POOL_SIZE),
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(CAPACITY),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
        logger.info("线程池并发查询工具类 || 线程池中允许的最大线程数:{} || 线程池中当前线程数量:{} || 线程空闲状态超时退出时间最大值:{} || 并发队列容量:{}",
                MAXIMUM_POOL_SIZE, callableList.size(),KEEP_ALIVE_TIME, CAPACITY);

        List<Future<T>> futureList = threadPoolExecutor.invokeAll(callableList);
        threadPoolExecutor.shutdown();
        return futureList.stream().map(tFuture -> {
            try {
                return tFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("线程池并发查询工具类 || 线程查询失败 || 线程池名称:{}", threadPoolName, e);
                Thread.currentThread().interrupt();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
