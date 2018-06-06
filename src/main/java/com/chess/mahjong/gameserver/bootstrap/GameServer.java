package com.chess.mahjong.gameserver.bootstrap;

import com.chess.mahjong.gameserver.commons.message.MsgDispatcher;
import com.chess.mahjong.gameserver.context.ExecutorServiceManager;
import com.chess.mahjong.gameserver.context.InitServers;
import com.chess.mahjong.gameserver.net.MinaHostMsgHandler;
import com.chess.mahjong.gameserver.net.MinaMsgHandler;
import com.chess.mahjong.gameserver.net.NetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 周润斌
 * @Date: create in 下午 3:38 2018-03-01
 * @Description:
 */
public class GameServer {

    private static Logger logger = LoggerFactory.getLogger(GameServer.class);
    private static NetManager netManager;

    private static GameServer instance=new GameServer();

    public static MsgDispatcher msgDispatcher = new MsgDispatcher();;

    private static int port = 8888;
    private static int hostPort = 6666;

    public GameServer(){
        netManager = new NetManager();
    }

    public static void main(String[] args) {
        //服务器启动
        startUp();
    }

    private static void startUp() {
        try {
            logger.info("开启数据库线程池....");
            ExecutorServiceManager.getInstance().initExecutorServiceForDB();
            InitServers.getInstance().initServersFun();
            netManager.startListener(new MinaMsgHandler(),port);
            logger.info("开启前端服务器,监听端口为{}",port);
            netManager.startHostListener(new MinaHostMsgHandler(),hostPort);
            logger.info("开启后端服务器,监听端口为{}",hostPort);
        }catch (Exception e){
            logger.error("服务器启动失败");
            e.printStackTrace();
        }
    }

}
