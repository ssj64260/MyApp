package com.cxb.tools.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 */

public class ThreadPoolUtil {

    private static int maxThread = 5;

    private static ThreadPoolUtil instance;

    //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    private ExecutorService cachedThreadPool = null;
    //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    private ExecutorService fixedThreadPool = null;
    //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    private ScheduledExecutorService scheduledThreadPool = null;
    //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)
    private ExecutorService singleThreadExecutor = null;

    private ThreadPoolUtil() {

    }

    public static ThreadPoolUtil getInstache() {
        if (instance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }

    public static void init(int maxThread){
        ThreadPoolUtil.maxThread = maxThread;
    }

    public void cachedExecute(Runnable runnable) {
        if (cachedThreadPool == null || cachedThreadPool.isShutdown()) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        cachedThreadPool.execute(runnable);
    }

    public void fixedExecute(Runnable runnable) {
        if (fixedThreadPool == null || fixedThreadPool.isShutdown()) {
            fixedThreadPool = Executors.newFixedThreadPool(maxThread);
        }
        fixedThreadPool.execute(runnable);
    }

    public void scheduled(Runnable runnable, long initialDelay, TimeUnit timeUnit) {
        if (scheduledThreadPool == null || scheduledThreadPool.isShutdown()) {
            scheduledThreadPool = Executors.newScheduledThreadPool(maxThread);
        }
        scheduledThreadPool.schedule(runnable, initialDelay, timeUnit);
    }

    public void scheduledRate(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit) {
        if (scheduledThreadPool == null || scheduledThreadPool.isShutdown()) {
            scheduledThreadPool = Executors.newScheduledThreadPool(maxThread);
        }
        scheduledThreadPool.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    public void scheduledDelay(Runnable runnable, long initialDelay, long delay, TimeUnit timeUnit) {
        if (scheduledThreadPool == null || scheduledThreadPool.isShutdown()) {
            scheduledThreadPool = Executors.newScheduledThreadPool(maxThread);
        }
        scheduledThreadPool.scheduleWithFixedDelay(runnable, initialDelay, delay, timeUnit);
    }

    public void singleExecute(Runnable runnable) {
        if (singleThreadExecutor == null || singleThreadExecutor.isShutdown()) {
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        singleThreadExecutor.execute(runnable);
    }

    public void cachedShutDown(long awaitTime) {
        setShutDown(cachedThreadPool, awaitTime);
    }

    public void fixedShutDown(long awaitTime) {
        setShutDown(fixedThreadPool, awaitTime);
    }

    public void scheduledShutDown(long awaitTime) {
        setShutDown(scheduledThreadPool, awaitTime);
    }

    public void singleShutDown(long awaitTime) {
        setShutDown(singleThreadExecutor, awaitTime);
    }

    private void setShutDown(ExecutorService executorService, long awaitTime) {
        if (executorService != null) {
            try {
                executorService.shutdown();

                // (所有的任务都结束的时候，返回TRUE)
                if (!executorService.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)) {
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                e.printStackTrace();
                executorService.shutdownNow();
            }
        }
    }
}
