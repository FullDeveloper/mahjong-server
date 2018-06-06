package com.chess.mahjong.gameserver.commons.tool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 周润斌
 * @Date: create in 下午 3:51 2018-03-01
 * @Description:
 */
public class ServerThreadFactory implements ThreadFactory{
    //线程组
    final ThreadGroup group;
    //线程编号
    final AtomicInteger threadNumber = new AtomicInteger(1);
    //线程名称前缀
    final String namePrefix;


    public ServerThreadFactory(String namePrefix) {
        SecurityManager securityManager = System.getSecurityManager();
        group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group,r,namePrefix + threadNumber.getAndIncrement(), 0);
        if(thread.isDaemon()){
            thread.setDaemon(false);
        }
        if(thread.getPriority() != Thread.NORM_PRIORITY){
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
