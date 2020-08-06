package com.crazyhuskar.myandroidsdk.util;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

/**
 * @author CrazyHuskar
 * @date 2018/6/11
 */
public class MyUtilThreadPool {
    private static ScheduledExecutorService scheduledExecutorService;
    private static MyUtilThreadPool instance = null;
    private static ThreadFactory threadFactory;

    public MyUtilThreadPool() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(10, threadFactory);
    }

    public static MyUtilThreadPool getInstance() {
        if (null == instance || null == scheduledExecutorService) {
            threadFactory = new ThreadFactory() {
                private AtomicInteger atoInteger = new AtomicInteger(0);

                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread t = new Thread(r, "MyThreadPool-" + atoInteger.getAndIncrement());
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }
                    if (t.getPriority() != Thread.NORM_PRIORITY) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                }
            };
            instance = new MyUtilThreadPool();
        }
        return instance;
    }

    public static void doShutdown(){
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdown();
        }
    }

    /**
     * 延时任务
     *
     * @param runnable
     * @param delay    延迟时间
     * @return
     */
    public ScheduledFuture schedule(Runnable runnable, long delay) {
        return scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
     *
     * @param runnable
     * @param delay    延迟时间
     * @param period   间隔时间
     * @return
     */
    public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long delay, long period) {
        return scheduledExecutorService.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环任务，以上一次任务的结束时间计算下一次任务的开始时间
     *
     * @param runnable
     * @param delay    延迟时间
     * @param period   间隔时间
     * @return
     */
    public ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long delay, long period) {
        return scheduledExecutorService.scheduleWithFixedDelay(runnable, delay, period, TimeUnit.MILLISECONDS);
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
