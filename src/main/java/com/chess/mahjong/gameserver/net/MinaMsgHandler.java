package com.chess.mahjong.gameserver.net;

import com.chess.mahjong.gameserver.bootstrap.GameServer;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:59 2018-03-01
 * @Description:
 */
public class MinaMsgHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MinaMsgHandler.class);

/*    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }*/

    /**
     * 会话打开时执行
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        //将自定义Session和当前用户session绑定
        new GameSession(session);
        logger.info("a session create from ip {}",session.getRemoteAddress());
        //super.sessionOpened(session);
    }

    /**
     * 收到消息时执行
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.info("a session send message  {}",message);
        ClientRequest clientRequest = (ClientRequest) message;
        GameSession gameSession = GameSession.getInstance(session);
        if (gameSession == null) {
            logger.info("gameSession == null");
            return;
        }
        GameServer.msgDispatcher.dispatchMsg(gameSession,clientRequest);
    }

    /**
     * 会话关闭时执行
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("a session closed ip:{}",session.getRemoteAddress());
        super.sessionClosed(session);
    }

    /**
     * 出错时执行
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }




}
