package com.chess.mahjong.gameserver.context;

import com.chess.mahjong.gameserver.commons.tool.ServerThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 周润斌
 * @Date: create in 下午 3:46 2018-03-01
 * @Description: 整个服务的线程池工具类
 */
public class ExecutorServiceManager {

    private static ExecutorServiceManager serviceManager = new ExecutorServiceManager();

    //数据库操作使用的线程池
    private ScheduledThreadPoolExecutor executorServiceForDB;

    public static ExecutorServiceManager getInstance(){
        return serviceManager;
    }

    /**
     * 初始化线程池对象
     */
    public void initExecutorServiceForDB(){
        executorServiceForDB = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(
                10,
                new ServerThreadFactory("executorServiceForDB"));
    }

    /**
     * 得到线程池对象
     * @return
     */
    public ScheduledThreadPoolExecutor getExecutorServiceForDB(){
        return executorServiceForDB;
    }

    public void stop() throws InterruptedException {
        executorServiceForDB.shutdown();
        executorServiceForDB.awaitTermination(1, TimeUnit.SECONDS);
    }


}
