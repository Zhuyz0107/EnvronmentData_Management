package com.briup.servers.receive.homework.testBlocking;

import java.nio.channels.ShutdownChannelGroupException;
import java.util.concurrent.*;

/**
 * @Auther: vanse(zyz)
 * @Date: 2023/9/26-09-26-9:48
 * @Description：com.briup.servers.receive.testBlocking
 *
 * 四种快速创建线程池的方法
 */
public class TestExecutors {
    public static void main(String[] args) {
        //定长线程池,核心跟最大一样，无限队列
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
        }

        //缓存线程池 核心是0 最大无限制 线程安全
        ExecutorService executorService1 = Executors.newCachedThreadPool();

        //单例线程 核心1 最大1 无限队列
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();


        // 线程池定时调度 核心指定，最大无限制，每15秒执行一次
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        //5秒后按每秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟10秒再执行,之后再按照每3秒执行一次");
            }
        },10,3, TimeUnit.SECONDS);
    }
}
